package com.android.ucptask.data.repository

import androidx.lifecycle.LiveData
import com.android.ucptask.data.db.CurrentWeatherDao
import com.android.ucptask.data.db.FutureWeatherDao
import com.android.ucptask.data.db.entity.CurrentWeatherResponse
import com.android.ucptask.data.db.entity.FutureWeatherResponse
import com.android.ucptask.data.db.unitlocalized.current.SpecificCurrentWeatherEntry
import com.android.ucptask.data.db.unitlocalized.future.detail.SpecificDetailFutureWeatherEntry
import com.android.ucptask.data.db.unitlocalized.future.list.SpecificSimpleFutureWeatherEntry
import com.android.ucptask.data.network.WeatherNetworkDataSource
import com.android.ucptask.data.provider.LocationProvider
import com.android.ucptask.data.provider.UnitProvider
import kotlinx.coroutines.*
import org.threeten.bp.ZonedDateTime
import java.util.*

const val FORECAST_EXCLUDE = "current,minutely,hourly"

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val futureWeatherDao: FutureWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource,
    private val unitProvider: UnitProvider,
    private val locationProvider: LocationProvider
) : ForecastRepository {

    init {
        weatherNetworkDataSource.apply {
            downloadedCurrentWeather.observeForever { newCurrentWeather ->
                persistFetchedCurrentWeather(newCurrentWeather)
            }
            downloadedFutureWeather.observeForever { newFutureWeather ->
                persistFetchedFutureWeather(newFutureWeather)
            }
        }
    }

    override suspend fun getCurrentWeather(): LiveData<out SpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext currentWeatherDao.getCurrentWeather()
        }
    }

    override suspend fun getFutureWeatherList(startDate: Long): LiveData<out List<SpecificSimpleFutureWeatherEntry>> {
        return withContext(Dispatchers.IO) {
            initFutureWeatherData()
            return@withContext futureWeatherDao.getSimpleWeatherForecast(startDate)
        }
    }

    override suspend fun getFutureWeatherByDate(date: Long): LiveData<out SpecificDetailFutureWeatherEntry> {
        return withContext(Dispatchers.IO) {
            return@withContext futureWeatherDao.getDetailWeatherForecast(date)
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather)
        }
    }

    private fun persistFetchedFutureWeather(fetchedWeather: FutureWeatherResponse) {
        fun deleteOldForecastData() {
            futureWeatherDao.deleteOldEntries()
        }

        GlobalScope.launch(Dispatchers.IO) {
            deleteOldForecastData()
            val futureWeatherList = fetchedWeather.daily
            futureWeatherDao.insert(futureWeatherList)
        }
    }

    private suspend fun initWeatherData() {
        val lastWeatherLocation = currentWeatherDao.getCurrentWeather().value

        if (lastWeatherLocation == null ||
            locationProvider.hasLocationChanged(lastWeatherLocation)
        ) {

            fetchCurrentWeather()

            return
        }

        if (isFetchCurrentNeeded(lastWeatherLocation.zonedDateTime))
            fetchCurrentWeather()

    }

    private suspend fun initFutureWeatherData() {
        val lastWeatherLocation = currentWeatherDao.getCurrentWeather().value

        if (lastWeatherLocation == null ||
            locationProvider.hasLocationChanged(lastWeatherLocation)
        ) {

            fetchFutureWeather()

            return
        }

        if (isFetchFutureNeeded())
            fetchFutureWeather()
    }

    private suspend fun fetchCurrentWeather() {
        weatherNetworkDataSource.fetchCurrentWeather(
            locationProvider.getPreferredLocationString(),
            unitProvider.getSystemUnit()
        )
    }

    private suspend fun fetchFutureWeather() {
        val lat = currentWeatherDao.getCurrentWeatherNonLive().latitude
        val lon = currentWeatherDao.getCurrentWeatherNonLive().longitude
        weatherNetworkDataSource.fetchFutureWeather(
            lat,
            lon,
            FORECAST_EXCLUDE,
            unitProvider.getSystemUnit()
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

    private fun isFetchFutureNeeded(): Boolean {
        val today = Date()
        val futureWeatherCount = futureWeatherDao.countFutureWeather(today.time)
        return futureWeatherCount < 7
    }
}