package com.android.ucptask.ui.weather.future.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.ucptask.R
import com.android.ucptask.data.db.unitlocalized.future.list.SpecificSimpleFutureWeatherEntry
import com.android.ucptask.ui.base.ScopedFragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.future_list_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class FutureListWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein: Kodein by closestKodein()
    private val viewModelFactory: FutureListWeatherViewModelFactory by instance()

    private lateinit var viewModel: FutureListWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(FutureListWeatherViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        val futureWeatherEntries = viewModel.weatherEntries.await()
        val weatherLocation = viewModel.locationName.await()

        weatherLocation.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            updateLocation(it.cityName)
        })

        futureWeatherEntries.observe(viewLifecycleOwner, Observer { weatherEntries ->
            if (weatherEntries == null) return@Observer

            group_loading.visibility = View.GONE

            initRecyclerView(weatherEntries.toFutureWeatherItems())

        })
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Next Week"
    }

    private fun initRecyclerView(items: List<FutureWeatherItem>) {
        val groupAdapter = GroupAdapter<ViewHolder>().apply {
            addAll(items)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@FutureListWeatherFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            (item as? FutureWeatherItem)?.let {
                showWeatherDetail(it.weatherEntry.date, view)
            }
        }
    }

    private fun showWeatherDetail(date: Long, view: View) {
        val actionDetail = FutureListWeatherFragmentDirections.actionDetail(date)
        Navigation.findNavController(view).navigate(actionDetail)
    }

    private fun List<SpecificSimpleFutureWeatherEntry>.toFutureWeatherItems(): List<FutureWeatherItem> {
        return this.map {
            FutureWeatherItem(it, this@FutureListWeatherFragment.context)
        }

    }

}