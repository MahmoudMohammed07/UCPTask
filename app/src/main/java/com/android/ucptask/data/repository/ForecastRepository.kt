package com.android.ucptask.data.repository

import androidx.lifecycle.LiveData
import com.android.ucptask.data.db.unitlocalized.current.SpecificCurrentWeatherEntry
import com.android.ucptask.data.db.unitlocalized.future.detail.SpecificDetailFutureWeatherEntry
import com.android.ucptask.data.db.unitlocalized.future.list.SpecificSimpleFutureWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(): LiveData<out SpecificCurrentWeatherEntry>
    suspend fun getFutureWeatherList(startDate: Long): LiveData<out List<SpecificSimpleFutureWeatherEntry>>
    suspend fun getFutureWeatherByDate(date: Long): LiveData<out SpecificDetailFutureWeatherEntry>
}