package com.android.ucptask.data.db.unitlocalized.future.detail

import androidx.room.ColumnInfo
import com.android.ucptask.data.db.entity.current.Weather

data class SpecificDetailFutureWeatherEntryImpl(
    @ColumnInfo(name = "date")
    override val date: Long,
    @ColumnInfo(name = "temp_day")
    override val avgTemperature: Double,
    @ColumnInfo(name = "temp_max")
    override val maxTemperature: Double,
    @ColumnInfo(name = "temp_min")
    override val minTemperature: Double,
    override val weather: List<Weather>,
    @ColumnInfo(name = "humidity")
    override val humidity: Int,
    @ColumnInfo(name = "pressure")
    override val pressure: Int,
    @ColumnInfo(name = "windSpeed")
    override val windSpeed: Double,
    @ColumnInfo(name = "uvi")
    override val uvi: Double
) : SpecificDetailFutureWeatherEntry