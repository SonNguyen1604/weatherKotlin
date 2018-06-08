package com.example.nguyenngocsonc.weatherkotlin.interfaces

import android.content.Context
import com.example.nguyenngocsonc.weatherkotlin.R
import com.example.nguyenngocsonc.weatherkotlin.models.weather.WeatherData
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    companion object {
        fun create(context: Context): WeatherAPI {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl(context.getString(R.string.server_url))
                    .build()

            return retrofit.create(WeatherAPI::class.java)
        }
    }

    @GET("weather/")
    fun getWeatherInfo(@Query("id") id: String, @Query("appid") appid: String) : Observable<WeatherData>
}