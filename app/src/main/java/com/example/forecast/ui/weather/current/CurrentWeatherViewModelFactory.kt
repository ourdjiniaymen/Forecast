package com.example.forecast.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.forecast.data.repository.ForecastRepository

/**we use factory to preserve view model state
factory will create a new viewModel if it is not yet created else it will return the already created instance**/
class CurrentWeatherViewModelFactory(
        private val forecastRepository: ForecastRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(forecastRepository) as T
    }
}