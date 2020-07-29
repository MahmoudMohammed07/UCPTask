package com.android.ucptask.data.db.entity.current

import androidx.room.ColumnInfo


data class Weather(
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "icon")
    val icon: String
)