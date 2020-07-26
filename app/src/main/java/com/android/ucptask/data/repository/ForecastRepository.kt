package com.android.ucptask.data.repository

import androidx.lifecycle.LiveData
import com.android.ucptask.data.db.unitlocalized.SpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather() : LiveData<out SpecificCurrentWeatherEntry>
}