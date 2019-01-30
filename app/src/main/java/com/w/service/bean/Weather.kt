package com.w.service.bean

data class Weather(
        val weatherInfo: String,
        val weatherImg: String)

data class WeatherList(
        val weatherCurrInfo: String,
        val weatherList: List<Weather>)













