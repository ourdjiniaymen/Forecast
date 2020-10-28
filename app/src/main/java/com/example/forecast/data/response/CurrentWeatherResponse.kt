package com.example.forecast.data.response


import com.example.forecast.data.response.Location
import com.example.forecast.data.response.Request
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: Location,
    val request: Request
)