package com.android.ucptask.ui.weather.future.list

import androidx.lifecycle.ViewModel
import com.android.ucptask.data.repository.ForecastRepository
import com.android.ucptask.util.lazyDeferred
import java.util.*

class FutureListWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    val weatherEntries by lazyDeferred {
        forecastRepository.getFutureWeatherList(Date().time)
    }

    val locationName by lazyDeferred {
        forecastRepository.getCurrentWeather()
    }
}