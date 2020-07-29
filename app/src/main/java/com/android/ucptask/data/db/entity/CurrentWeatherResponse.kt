package com.android.ucptask.data.db.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.ucptask.data.db.entity.current.Coordinates
import com.android.ucptask.data.db.entity.current.CurrentWeatherEntry
import com.android.ucptask.data.db.entity.current.Weather
import com.android.ucptask.data.db.entity.current.Wind
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherResponse(
    @SerializedName("dt")
    val date: Long,
    @SerializedName("id")
    val timezoneId: String,
    @SerializedName("main")
    @Embedded(prefix = "currentWeatherEntry_")
    val currentWeatherEntry: CurrentWeatherEntry,
    val name: String,
    val visibility: Int,
    val weather: List<Weather>,
    @Embedded(prefix = "wind_")
    val wind: Wind,
    @Embedded(prefix = "coord_")
    val coord: Coordinates
) {
    @PrimaryKey(autoGenerate = false)
    var KEY_ID: Int = CURRENT_WEATHER_ID

}