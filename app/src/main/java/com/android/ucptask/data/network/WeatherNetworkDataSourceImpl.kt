package com.android.ucptask.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.ucptask.data.db.entity.CurrentWeatherResponse
import com.android.ucptask.data.db.entity.FutureWeatherResponse
import com.android.ucptask.util.NoConnectivityException

class WeatherNetworkDataSourceImpl(private val weatherApiService: WeatherApiService) :
    WeatherNetworkDataSource {

    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    private val _downloadedFutureWeather = MutableLiveData<FutureWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override val downloadedFutureWeather: LiveData<FutureWeatherResponse>
        get() = _downloadedFutureWeather

    override suspend fun fetchCurrentWeather(location: String, unit: String) {
        try {
            val fetchedCurrentWeather = weatherApiService
                .getCurrentWeatherAsync(location, unit)
                .await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet Connection. ", e)
        }
    }

    override suspend fun fetchFutureWeather(
        lat: Double,
        lon: Double,
        exclude: String,
        units: String
    ) {
        try {
            val fetchedFutureWeather = weatherApiService
                .getFutureWeatherAsync(lat, lon, exclude, units)
                .await()
            _downloadedFutureWeather.postValue(fetchedFutureWeather)
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No Internet Connection. ", e)
        }
    }


}