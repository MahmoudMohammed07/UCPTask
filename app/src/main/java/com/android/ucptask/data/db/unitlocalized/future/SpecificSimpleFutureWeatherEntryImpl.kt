package com.android.ucptask.data.db.unitlocalized.future

import androidx.room.ColumnInfo
import com.android.ucptask.data.db.entity.current.Weather

class SpecificSimpleFutureWeatherEntryImpl(
    @ColumnInfo(name = "date")
    override val date: Long,
    @ColumnInfo(name = "temp_day")
    override val temperature: Double,
    override val weather: List<Weather>
) : SpecificSimpleFutureWeatherEntry