package com.android.ucptask.data.db.unitlocalized.future.detail

import com.android.ucptask.data.db.entity.current.Weather

interface SpecificDetailFutureWeatherEntry {
    val date: Long
    val avgTemperature: Double
    val maxTemperature: Double
    val minTemperature: Double
    val weather: List<Weather>
    val humidity: Int
    val pressure: Int
    val windSpeed: Double
    val uvi: Double
}