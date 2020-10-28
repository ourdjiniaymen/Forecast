package com.example.forecast.data.network.implementation

import com.example.forecast.data.network.abstraction.Api
import com.example.forecast.data.network.abstraction.SERVER_URL
import kotlinx.coroutines.Deferred
import com.example.forecast.data.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL: String = SERVER_URL

interface CurrentWeatherApi : Api {
    @GET("current")
    fun getCurrentWeatherAsync(
            @Query("query") city : String,
            @Query("units") unit: String = "m"
    ):Deferred<CurrentWeatherResponse>


   companion object{
       operator fun invoke():CurrentWeatherApi{//implementation (creation) of CurrentWeatherApi through Retrofit
           return Api().baseUrl(BASE_URL)
                   .addCallAdapterFactory(CoroutineCallAdapterFactory())
                   .build()
                   .create(CurrentWeatherApi::class.java)
       }
   }
}