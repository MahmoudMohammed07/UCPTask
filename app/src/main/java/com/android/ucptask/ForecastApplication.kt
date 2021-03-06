package com.android.ucptask

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.android.ucptask.data.db.ForecastDatabase
import com.android.ucptask.data.network.*
import com.android.ucptask.data.provider.LocationProvider
import com.android.ucptask.data.provider.LocationProviderImpl
import com.android.ucptask.data.provider.UnitProvider
import com.android.ucptask.data.provider.UnitProviderImpl
import com.android.ucptask.data.repository.ForecastRepository
import com.android.ucptask.data.repository.ForecastRepositoryImpl
import com.android.ucptask.ui.weather.current.CurrentWeatherViewModelFactory
import com.android.ucptask.ui.weather.future.detail.FutureDetailWeatherViewModelFactory
import com.android.ucptask.ui.weather.future.list.FutureListWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.*

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().futureWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind<ForecastRepository>() with singleton {
            ForecastRepositoryImpl(
                instance(),
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
        bind() from provider { FutureListWeatherViewModelFactory(instance()) }
        bind() from factory { date: Long ->
            FutureDetailWeatherViewModelFactory(
                instance(),
                date,
                instance()
            )
        }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}