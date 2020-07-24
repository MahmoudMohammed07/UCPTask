package com.android.ucptask.data.db.unitlocalized

interface SpecificCurrentWeatherEntry {
    val date:Int
    val feelsLikeTemperature: Double
    val humidity: Int
    val pressure: Int
    val temperature: Int
    val tempMax: Int
    val tempMin: Int
    val cityName: String
    val visibility: Int
    val weatherDescription: String
    val weatherIcon: String
    val windSpeed: Double
}