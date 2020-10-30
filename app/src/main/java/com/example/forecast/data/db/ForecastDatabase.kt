package com.example.forecast.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.forecast.data.db.dao.CurrentWeatherDao
import com.example.forecast.data.db.entity.CurrentWeatherEntry

@Database(
        entities = [CurrentWeatherEntry::class],
        version = 1,
        exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao//return an implemented dao

    companion object { //so the db be a singleton
        @Volatile //all threads will have immediate access to instance
        private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
                ?: synchronized(LOCK) {
            instance
                    ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        ForecastDatabase::class.java, "forecast.db")
                        .build()

    }
}