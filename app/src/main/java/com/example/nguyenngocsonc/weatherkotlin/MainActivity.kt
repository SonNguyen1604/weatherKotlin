package com.example.nguyenngocsonc.weatherkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.example.nguyenngocsonc.weatherkotlin.interfaces.ImageAPI
import com.example.nguyenngocsonc.weatherkotlin.interfaces.WeatherAPI
import com.example.nguyenngocsonc.weatherkotlin.models.images.Photos
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var context = this@MainActivity

    private val weather by lazy {
        WeatherAPI.create(context)
    }

    private val imageWeather by lazy {
        ImageAPI.create(context)
    }

    private lateinit var disposable: CompositeDisposable
    private lateinit var dialogLoading: MaterialDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        disposable = CompositeDisposable()
        dialogLoading = MaterialDialog.Builder(this)
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .progress(true, 0)
                .build()
        dialogLoading.hide()

        getImage("sunny")

    }

    private fun getWeather() {
        disposable.add(weather.getWeatherInfo(context.getString(R.string.hanoi_id), context.getString(R.string.appid))
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    runOnUiThread {
                        dialogLoading.show()
                    }
                }
                .doOnError {
                    runOnUiThread {
                        runOnUiThread {
                            dialogLoading.dismiss()
                        }
                    }
                }
                .doOnComplete {
                    runOnUiThread {
                        dialogLoading.dismiss()
                    }
                }
                .subscribe(
                        { result -> getImage(result.name) },
                        { error -> Log.d("weather", error.toString()) }
                ))
    }

    private fun getImage(tag: String) {
        disposable.add(imageWeather.getImageWeather("flickr.photos.search", getString(R.string.flickr_key), tag, 1, "photo", "json", 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    runOnUiThread {
                        dialogLoading.show()
                    }
                }
                .doOnError {
                    runOnUiThread {
                        runOnUiThread {
                            dialogLoading.dismiss()
                        }
                    }
                }
                .doOnComplete {
                    runOnUiThread {
                        dialogLoading.dismiss()
                    }
                }
                .subscribe(
                        { result ->
                            showImage(result.photos)
                        },
                        { error ->
                            Log.d("weather", error.toString())
                        }))
    }

    private fun showImage(photos: Photos) {
        var randomNumber = (0..photos.photo.size).shuffled().last()
        var photo = photos.photo[randomNumber]
        var photoURL = "https://farm" + photo.farm + ".staticflickr.com/" + photo.server + "/" + photo.id + "_" + photo.secret +"_b.jpg"
        Log.d("image", photoURL)
        Glide.with(context).load(photoURL).into(imageWeatherImg)
        runOnUiThread {
            dialogLoading.dismiss()
        }
    }

    override fun onDestroy() {
        disposable.clear()
        dialogLoading.dismiss()
        super.onDestroy()
    }
}