package com.android.ucptask.data.db.entity.future

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.android.ucptask.data.db.entity.current.Weather
import com.google.gson.annotations.SerializedName

@Entity(tableName = "future_weather", indices = [Index(value = ["date"], unique = true)])
data class DailyForecast(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @SerializedName("dt")
    val date: Long,
    @Embedded(prefix = "temp_")
    val temp: Temp,
    val weather: List<Weather>,
    val humidity: Int,
    val pressure: Int,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    val uvi: Double
)
