package com.android.ucptask.data.network

import androidx.lifecycle.LiveData
import com.android.ucptask.data.db.entity.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(location: String, unit: String)
}