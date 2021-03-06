package com.android.ucptask.data.network

import com.android.ucptask.data.db.entity.CurrentWeatherResponse
import com.android.ucptask.data.db.entity.FutureWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "6400a07d3014a23d1de7bc70643728e9"
const val BASE_URL = "https://api.openweathermap.org/"

interface WeatherApiService {
    //http://api.openweathermap.org/data/2.5/weather?q=Cairo&units=metric&APPID=<apikey>
    @GET("data/2.5/weather")
    fun getCurrentWeatherAsync(
        @Query("q") location: String,
        @Query("units") unit: String
    ): Deferred<CurrentWeatherResponse>

    //https://api.openweathermap.org/data/2.5/onecall?lat=30.06&lon=31.25&exclude=current,minutely,hourly&units=metric&appid=<apikey>
    @GET("data/2.5/onecall")
    fun getFutureWeatherAsync(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String,
        @Query("units") units: String
    ): Deferred<FutureWeatherResponse>

    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): WeatherApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter(
                        "APPID",
                        API_KEY
                    )
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)
        }
    }
}