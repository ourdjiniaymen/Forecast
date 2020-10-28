package com.example.forecast.data.network.abstraction

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val SERVER_URL = "http://api.weatherstack.com/"
const val API_KEY = "fa99d972631f13e488d58edd8580162a"


interface Api {

    companion object {
        operator fun invoke(): Retrofit.Builder {

            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("access_key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }

            val httpClient = OkHttpClient.Builder()
                .callTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(1000, TimeUnit.SECONDS)
                .readTimeout(1000, TimeUnit.SECONDS)
                .writeTimeout(1000, TimeUnit.SECONDS)
                .addInterceptor(requestInterceptor)
                .build()


            return Retrofit.Builder()
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
        }
    }

}