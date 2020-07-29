package com.android.ucptask.data.db.unitlocalized.current

import com.android.ucptask.data.db.entity.current.Weather
import org.threeten.bp.ZonedDateTime

interface SpecificCurrentWeatherEntry {
    val date: Long
    val timezoneId: String
    val feelsLikeTemperature: Double
    val humidity: Int
    val pressure: Int
    val temperature: Double
    val tempMax: Int
    val tempMin: Int
    val cityName: String
    val visibility: Int
    val weather: List<Weather>
    val windSpeed: Double
    val latitude: Double
    val longitude: Double
    val zonedDateTime: ZonedDateTime
}