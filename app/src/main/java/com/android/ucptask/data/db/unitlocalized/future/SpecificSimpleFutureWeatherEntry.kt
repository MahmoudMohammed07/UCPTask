package com.android.ucptask.data.db.unitlocalized.future

import com.android.ucptask.data.db.entity.current.Weather

interface SpecificSimpleFutureWeatherEntry {
    val date: Long
    val temperature: Double
    val weather: List<Weather>
}