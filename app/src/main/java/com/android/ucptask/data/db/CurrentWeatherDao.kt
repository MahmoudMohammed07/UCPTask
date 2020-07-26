package com.android.ucptask.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.ucptask.data.db.entity.CURRENT_WEATHER_ID
import com.android.ucptask.data.db.entity.CurrentWeatherResponse
import com.android.ucptask.data.db.unitlocalized.SpecificCurrentWeatherEntryImpl

@Dao
interface CurrentWeatherDao {
    // insert or update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherResponse: CurrentWeatherResponse)

    @Query("SELECT * FROM current_weather WHERE KEY_ID = $CURRENT_WEATHER_ID")
    fun getCurrentWeather(): LiveData<SpecificCurrentWeatherEntryImpl>
}