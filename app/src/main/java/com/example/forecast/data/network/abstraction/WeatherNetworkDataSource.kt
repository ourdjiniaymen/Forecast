package com.example.forecast.data.network.abstraction

import androidx.lifecycle.LiveData
import com.example.forecast.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
            location: String,
            unit: String = "m"
    )
}