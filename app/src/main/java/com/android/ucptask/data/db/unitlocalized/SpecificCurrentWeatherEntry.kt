package com.android.ucptask.data.db.unitlocalized

import com.android.ucptask.data.db.entity.Weather

interface SpecificCurrentWeatherEntry {
    val date:Int
    val timezoneId: String
    val feelsLikeTemperature: Double
    val humidity: Int
    val pressure: Int
    val temperature: Int
    val tempMax: Int
    val tempMin: Int
    val cityName: String
    val visibility: Int
    val weather: List<Weather>
    val windSpeed: Double
}