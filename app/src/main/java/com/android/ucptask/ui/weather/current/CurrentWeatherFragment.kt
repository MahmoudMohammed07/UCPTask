package com.android.ucptask.ui.weather.current

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.ucptask.R
import com.android.ucptask.data.db.entity.Weather
import com.android.ucptask.data.network.ConnectivityInterceptorImpl
import com.android.ucptask.data.network.WeatherApiService
import com.android.ucptask.data.network.WeatherNetworkDataSourceImpl
import com.android.ucptask.ui.base.ScopedFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(CurrentWeatherViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer

            group_loading.visibility = View.GONE
            updateLocation("Cairo")
            updateDateToToday()
            updateTemperature(it.temperature,it.feelsLikeTemperature)
            updateCondition(it.weather[0].description)
            updateHumidity(it.humidity)
            updateWind(it.windSpeed)
            updateVisibility(it.visibility)

            val iconId = it.weather[0].icon
            Glide.with(this@CurrentWeatherFragment)
                .load("http://openweathermap.org/img/wn/${iconId}@2x.png")
                .into(imageView_condition_icon)
        })
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperature(temperature: Double, feelsLike: Double) {
        textView_temperature.text = "$temperature°C"
        textView_feels_like_temperature.text = "Feels like $feelsLike°C"
    }

    private fun updateCondition(condition: String) {
        textView_condition.text = condition
    }

    private fun updateHumidity(humidity: Int) {
        textView_humidity.text = "Humidity: $humidity"
    }

    private fun updateWind(windSpeed: Double) {
        textView_wind.text = "Wind Speed: $windSpeed"
    }

    private fun updateVisibility(visibilityDistance: Int) {
        textView_visibility.text = "Visibility: $visibilityDistance"
    }

}