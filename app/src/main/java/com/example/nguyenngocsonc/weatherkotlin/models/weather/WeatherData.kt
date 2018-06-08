package com.example.nguyenngocsonc.weatherkotlin.models.weather

data class WeatherData(
        var coord: Coord,
        var weather: ArrayList<Weather>,
        var base: String,
        var main: Main,
        var visibility: Int,
        var wind: Wind,
        var clouds: Clouds,
        var dt: Int,
        var sys: Sys,
        var id: Int,
        var name: String,
        var cod: Int)