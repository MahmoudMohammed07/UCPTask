package com.android.ucptask.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import com.android.ucptask.data.provider.UnitProvider
import com.android.ucptask.data.repository.ForecastRepository
import com.android.ucptask.util.lazyDeferred

class FutureDetailWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    private val date: Long,
    unitProvider: UnitProvider
) : ViewModel() {
    private val unitSystem = unitProvider.getSystemUnit()

    val isMetric: Boolean
        get() = unitSystem.contentEquals("metric")

    val weather by lazyDeferred {
        forecastRepository.getFutureWeatherByDate(date)
    }

    val locationName by lazyDeferred {
        forecastRepository.getCurrentWeather()
    }

}