package com.example.forecast.data.network.implementation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forecast.data.network.WeatherApiService
import com.example.forecast.data.network.abstraction.WeatherNetworkDataSource
import com.example.forecast.data.network.response.CurrentWeatherResponse
import com.example.forecast.internal.NoConnectivityException

class WeatherNetworkDataSourceImpl(
        private val weatherApiService: WeatherApiService
) : WeatherNetworkDataSource {
    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, unit: String) {
        try {
            val fetchedCurrentWeather = weatherApiService.getCurrentWeatherAsync(location, unit).await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        } catch (exception: NoConnectivityException) {
            Log.e("Connectivity", "No Internet Connection", exception)
        }
    }
}