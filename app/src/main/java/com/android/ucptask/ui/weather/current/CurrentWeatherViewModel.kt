package com.android.ucptask.ui.weather.current

import androidx.lifecycle.ViewModel
import com.android.ucptask.data.repository.ForecastRepository
import com.android.ucptask.util.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather()
    }

}