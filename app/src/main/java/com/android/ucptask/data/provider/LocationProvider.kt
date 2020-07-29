package com.android.ucptask.data.provider

import com.android.ucptask.data.db.unitlocalized.current.SpecificCurrentWeatherEntry

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: SpecificCurrentWeatherEntry): Boolean
    suspend fun getPreferredLocationString(): String
}