package com.android.ucptask.ui.weather.future.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.ucptask.data.provider.UnitProvider
import com.android.ucptask.data.repository.ForecastRepository

class FutureDetailWeatherViewModelFactory(
    private val forecastRepository: ForecastRepository,
    private val date: Long,
    private val unitProvider: UnitProvider
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FutureDetailWeatherViewModel(forecastRepository, date, unitProvider) as T
    }
}