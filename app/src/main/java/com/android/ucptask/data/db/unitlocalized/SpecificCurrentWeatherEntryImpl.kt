package com.android.ucptask.data.db.unitlocalized

import androidx.room.ColumnInfo

data class SpecificCurrentWeatherEntryImpl(
    @ColumnInfo(name = "date")
    override val date: Int,
    @ColumnInfo(name = "currentWeatherEntry_feelsLike")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "currentWeatherEntry_humidity")
    override val humidity: Int,
    @ColumnInfo(name = "currentWeatherEntry_pressure")
    override val pressure: Int,
    @ColumnInfo(name = "currentWeatherEntry_temp")
    override val temperature: Int,
    @ColumnInfo(name = "currentWeatherEntry_tempMax")
    override val tempMax: Int,
    @ColumnInfo(name = "currentWeatherEntry_tempMin")
    override val tempMin: Int,
    @ColumnInfo(name = "name")
    override val cityName: String,
    @ColumnInfo(name = "visibility")
    override val visibility: Int,
    @ColumnInfo(name = "weather_description")
    override val weatherDescription: String,
    @ColumnInfo(name = "weather_icon")
    override val weatherIcon: String,
    @ColumnInfo(name = "wind_speed")
    override val windSpeed: Double
) : SpecificCurrentWeatherEntry