package com.android.ucptask.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.ucptask.data.db.entity.future.DailyForecast
import com.android.ucptask.data.db.unitlocalized.future.SpecificSimpleFutureWeatherEntryImpl

@Dao
interface FutureWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(futureWeatherEntries: List<DailyForecast>)

    @Query("SELECT * FROM future_weather WHERE date(date) >= date(:startDate)")
    fun getSimpleWeatherForecast(startDate: Long): LiveData<List<SpecificSimpleFutureWeatherEntryImpl>>

    @Query("SELECT count(id) FROM future_weather WHERE date(date) >= date(:startDate)")
    fun countFutureWeather(startDate: Long): Int

    @Query("DELETE FROM future_weather")
    fun deleteOldEntries()
}