package com.example.forecast

import android.app.Application
import com.example.forecast.data.db.ForecastDatabase
import com.example.forecast.data.network.WeatherApiService
import com.example.forecast.data.network.abstraction.ConnectivityInterceptor
import com.example.forecast.data.network.abstraction.WeatherNetworkDataSource
import com.example.forecast.data.network.implementation.ConnectivityInterceptorImpl
import com.example.forecast.data.network.implementation.WeatherNetworkDataSourceImpl
import com.example.forecast.data.repository.ForecastRepository
import com.example.forecast.data.repository.ForecastRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))//provide us with instances of : context, various services and anything related to android

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance()) }
    }
} 