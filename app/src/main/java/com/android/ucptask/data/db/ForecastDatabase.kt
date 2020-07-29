package com.android.ucptask.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.ucptask.data.db.entity.CurrentWeatherResponse
import com.android.ucptask.data.db.entity.future.DailyForecast
import com.android.ucptask.data.db.unitlocalized.Converters

@Database(entities = [CurrentWeatherResponse::class, DailyForecast::class], version = 1)
@TypeConverters(Converters::class)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun futureWeatherDao(): FutureWeatherDao

    companion object {
        @Volatile
        private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ForecastDatabase::class.java,
                "forecast.db"
            )
                .build()
    }
}