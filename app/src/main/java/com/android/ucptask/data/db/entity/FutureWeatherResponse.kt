package com.android.ucptask.data.db.entity

import com.android.ucptask.data.db.entity.future.DailyForecast

data class FutureWeatherResponse(val daily: List<DailyForecast>)