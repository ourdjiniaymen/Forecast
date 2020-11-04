package com.example.forecast.ui.weather.current

import androidx.lifecycle.ViewModel
import com.example.forecast.data.provider.UnitProvider
import com.example.forecast.data.repository.ForecastRepository
import com.example.forecast.internal.UnitSystem
import com.example.forecast.internal.lazyDeferred

class CurrentWeatherViewModel(
        private val forecastRepository: ForecastRepository,
        unitProvider: UnitProvider
) : ViewModel() {
    val unitSystem = unitProvider.getUnitSystem()
    private val unit: String
        get() = when (unitSystem) {
            UnitSystem.METRIC -> "m"
            UnitSystem.SCIENTIFIC -> "s"
            UnitSystem.FAHRENHEIT -> "f"
        }

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(unit)
    }
}
