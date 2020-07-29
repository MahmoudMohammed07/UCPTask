package com.android.ucptask.data.repository

import androidx.lifecycle.LiveData
import com.android.ucptask.data.db.unitlocalized.current.SpecificCurrentWeatherEntry
import com.android.ucptask.data.db.unitlocalized.future.SpecificSimpleFutureWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(): LiveData<out SpecificCurrentWeatherEntry>
    suspend fun getFutureWeatherList(startDate: Long): LiveData<out List<SpecificSimpleFutureWeatherEntry>>
}