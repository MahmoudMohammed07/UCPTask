package com.android.ucptask.ui.weather.current

import androidx.lifecycle.ViewModel
import com.android.ucptask.data.provider.UnitProvider
import com.android.ucptask.data.repository.ForecastRepository
import com.android.ucptask.util.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {
    private val unitSystem = unitProvider.getSystemUnit()

    val isMetric: Boolean
        get() = unitSystem.contentEquals("metric")

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather()
    }

}