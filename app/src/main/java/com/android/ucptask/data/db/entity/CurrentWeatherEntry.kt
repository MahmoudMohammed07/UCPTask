package com.android.ucptask.data.db.entity


import com.google.gson.annotations.SerializedName

data class CurrentWeatherEntry(
    @SerializedName("feels_like")
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Int,
    @SerializedName("temp_max")
    val tempMax: Int,
    @SerializedName("temp_min")
    val tempMin: Int
)