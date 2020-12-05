package com.example.willigetwet.utility

import com.example.willigetwet.model.WeatherForecast

class WeatherDataProcessor() {

    private fun getMappedForecasts(weatherForecasts: MutableList<WeatherForecast>):
            Map<String, MutableList<WeatherForecast>> {
        val map = mutableMapOf<String, MutableList<WeatherForecast>>()
        var today = weatherForecasts.removeAt(0)
        weatherForecasts.removeAll { it.dtTxt.subSequence(0, 10) == today.dtTxt.subSequence(0,10) }

        for (forecast in weatherForecasts) {
            if (map[forecast.dtTxt.substring(0, 10)] == null) {
                val forecastArr = mutableListOf<WeatherForecast>()
                forecastArr.add(forecast)
                map[forecast.dtTxt.substring(0,10)] = forecastArr
            } else {
                map[forecast.dtTxt.substring(0, 10)]?.add(forecast)
            }
        }
        return map
    }

    fun getForecastForNextFiveDays(weatherForecasts: MutableList<WeatherForecast>):
            List<WeatherForecast> {
        val result = mutableListOf<WeatherForecast>()
        val mappedByDay = getMappedForecasts(weatherForecasts)

        for ((date, forecasts) in mappedByDay) {
            val sorted = forecasts.sortedBy { it.main?.temp }
            result.add(sorted[sorted.size / 2])
        }

        return result
    }


}