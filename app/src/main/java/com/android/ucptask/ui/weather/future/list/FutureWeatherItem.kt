package com.android.ucptask.ui.weather.future.list

import android.content.Context
import com.android.ucptask.R
import com.android.ucptask.data.db.unitlocalized.future.list.SpecificSimpleFutureWeatherEntry
import com.android.ucptask.data.provider.UnitProviderImpl
import com.bumptech.glide.Glide
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_future_weather.*
import java.text.SimpleDateFormat
import java.util.*

private val DATE_FORMAT = SimpleDateFormat("dd-MM-yyyy")

class FutureWeatherItem(
    val weatherEntry: SpecificSimpleFutureWeatherEntry,
    val context: Context?
) : Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            textView_condition.text = weatherEntry.weather[0].description
            updateDate()
            updateTemperature()
            updateConditionImage()
        }
    }

    override fun getLayout() = R.layout.item_future_weather

    private fun ViewHolder.updateDate() {
        textView_date.text = DATE_FORMAT.format(Date(weatherEntry.date * 1000))
    }

    private fun ViewHolder.updateTemperature() {
        val unitSystem = UnitProviderImpl(context!!).getSystemUnit()
        val unitAbbreviation = if (unitSystem == "metric") "°C"
        else "°F"
        textView_temperature.text = "${weatherEntry.temperature}$unitAbbreviation"
    }

    private fun ViewHolder.updateConditionImage() {
        val iconId = weatherEntry.weather[0].icon
        Glide.with(this.containerView)
            .load("http://openweathermap.org/img/wn/${iconId}@2x.png")
            .into(imageView_condition_icon)
    }
}