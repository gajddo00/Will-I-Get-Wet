package com.example.willigetwet.utility

import com.example.willigetwet.model.WeatherForecast

class WeatherDataProcessor(weatherForecasts: MutableList<WeatherForecast>) {
    private val weatherForecasts: MutableList<WeatherForecast> = mutableListOf()

    fun getMappedForecasts(): Map<String, MutableList<WeatherForecast>> {
        val map = mutableMapOf<String, MutableList<WeatherForecast>>()
        for (forecast in weatherForecasts) {
            if (map[forecast.dtTxt] == null) {
                val forecastArr = mutableListOf<WeatherForecast>()
                forecastArr.add(forecast)
                map[forecast.dtTxt] = forecastArr
            } else {
                map[forecast.dtTxt]?.add(forecast)
            }
        }
        return map
    }


}