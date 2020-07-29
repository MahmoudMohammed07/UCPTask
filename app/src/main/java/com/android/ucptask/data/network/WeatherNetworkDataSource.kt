package com.android.ucptask.data.network

import androidx.lifecycle.LiveData
import com.android.ucptask.data.db.entity.CurrentWeatherResponse
import com.android.ucptask.data.db.entity.FutureWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(location: String, unit: String)
    suspend fun fetchFutureWeather(
        lat: Double,
        lon: Double,
        exclude: String,
        units: String
    )
}