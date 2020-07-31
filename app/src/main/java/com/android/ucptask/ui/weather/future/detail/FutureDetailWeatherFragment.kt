package com.android.ucptask.ui.weather.future.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.ucptask.R
import com.android.ucptask.ui.base.ScopedFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.future_detail_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.factory
import java.text.SimpleDateFormat
import java.util.*

private val DATE_FORMAT = SimpleDateFormat("dd-MM-yyyy")

class FutureDetailWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val viewModelFactoryInstanceFactory:
            ((Long) -> FutureDetailWeatherViewModelFactory) by factory()

    private lateinit var viewModel: FutureDetailWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_detail_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val safeArgs = arguments?.let {
            FutureDetailWeatherFragmentArgs.fromBundle(it)
        }
        val date = safeArgs?.date

        viewModel = ViewModelProvider(this, viewModelFactoryInstanceFactory(date!!))
            .get(FutureDetailWeatherViewModel::class.java)

        bindUi()
    }

    private fun bindUi() = launch(Dispatchers.Main) {
        val futureWeather = viewModel.weather.await()
        val weatherLocation = viewModel.locationName.await()

        weatherLocation.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            updateLocation(it.cityName)
        })

        futureWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            updateDate(it.date)
            updateTemperatures(it.avgTemperature, it.maxTemperature, it.minTemperature)
            updateCondition(it.weather[0].description)
            updateHumidity(it.humidity)
            updateWind(it.windSpeed)
            updatePressure(it.pressure)
            updateUvi(it.uvi)

            val iconId = it.weather[0].icon
            Glide.with(this@FutureDetailWeatherFragment)
                .load("https://openweathermap.org/img/wn/${iconId}@2x.png")
                .into(imageView_condition_icon)
        })
    }

    private fun chooseLocalizedUnitAbbreviation(metric: String, imperial: String): String {
        return if (viewModel.isMetric) metric else imperial
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDate(date: Long) {
        val dateString = DATE_FORMAT.format(Date(date * 1000))
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = dateString
    }

    private fun updateTemperatures(temperature: Double, max: Double, min: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("°C", "°F")
        textView_temperature.text = "$temperature$unitAbbreviation"
        textView_min_max_temperature.text = "Max: $max$unitAbbreviation, Min: $min$unitAbbreviation"
    }

    private fun updateCondition(condition: String) {
        textView_condition.text = condition
    }

    private fun updateHumidity(humidity: Int) {
        textView_humidity.text = "Humidity: $humidity"
    }

    private fun updateWind(windSpeed: Double) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviation("kph", "mph")
        textView_wind.text = "Wind Speed: $windSpeed$unitAbbreviation"
    }

    private fun updatePressure(pressure: Int) {
        textView_pressure.text = "Pressure: $pressure"
    }

    private fun updateUvi(uv: Double) {
        textView_uv.text = "UV: $uv"
    }

}