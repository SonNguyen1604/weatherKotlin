package com.example.nguyenngocsonc.weatherkotlin.interfaces

import android.content.Context
import com.example.nguyenngocsonc.weatherkotlin.R
import com.example.nguyenngocsonc.weatherkotlin.models.images.ImageWeather
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ImageAPI {
    companion object {
        fun create(context: Context): ImageAPI {

            val gson = GsonBuilder()
                    .setLenient()
                    .create()

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create(gson))
                    .baseUrl(context.getString(R.string.server_image_url))
                    .build()

            return retrofit.create(ImageAPI::class.java)
        }
    }

    @GET("services/rest")
    fun getImageWeather(@Query("method") method: String, @Query("api_key") api_key: String,
                        @Query("tags") tags: String, @Query("safe_search") safe_search: Int, @Query("media") media: String,
                        @Query("format") format: String, @Query("nojsoncallback") nojsoncallback: Int) : Observable<ImageWeather>
}