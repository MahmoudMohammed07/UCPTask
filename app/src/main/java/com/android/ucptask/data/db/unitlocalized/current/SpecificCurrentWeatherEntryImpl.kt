package com.android.ucptask.data.db.unitlocalized.current

import androidx.room.ColumnInfo
import com.android.ucptask.data.db.entity.current.Weather
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

data class SpecificCurrentWeatherEntryImpl(
    @ColumnInfo(name = "date")
    override val date: Long,
    @ColumnInfo(name = "currentWeatherEntry_feelsLike")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "currentWeatherEntry_humidity")
    override val humidity: Int,
    @ColumnInfo(name = "currentWeatherEntry_pressure")
    override val pressure: Int,
    @ColumnInfo(name = "currentWeatherEntry_temp")
    override val temperature: Double,
    @ColumnInfo(name = "currentWeatherEntry_tempMax")
    override val tempMax: Int,
    @ColumnInfo(name = "currentWeatherEntry_tempMin")
    override val tempMin: Int,
    @ColumnInfo(name = "name")
    override val cityName: String,
    @ColumnInfo(name = "visibility")
    override val visibility: Int,
    override val weather: List<Weather>,
    @ColumnInfo(name = "wind_speed")
    override val windSpeed: Double,
    @ColumnInfo(name = "timezoneId")
    override val timezoneId: String,
    @ColumnInfo(name = "coord_lat")
    override val latitude: Double,
    @ColumnInfo(name = "coord_lon")
    override val longitude: Double
) : SpecificCurrentWeatherEntry {
    override val zonedDateTime: ZonedDateTime
        get() {
            val instant = Instant.ofEpochSecond(date)
            val zoneId = ZoneId.of(timezoneId)
            return ZonedDateTime.ofInstant(instant, zoneId)
        }
}