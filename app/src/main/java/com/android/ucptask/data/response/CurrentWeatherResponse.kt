package com.android.ucptask.data.response


import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("dt")
    val date: Int,
    val main: Main,
    val name: String,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)