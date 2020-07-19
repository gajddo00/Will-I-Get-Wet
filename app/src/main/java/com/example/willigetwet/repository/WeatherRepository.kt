package com.example.willigetwet.repository

import com.example.willigetwet.api.WeatherAPIProvider
import com.example.willigetwet.api.WeatherService

class WeatherRepository {
    var client: WeatherService = WeatherAPIProvider.retrofit

    fun getWeeklyWeatherForecast(cityNameAndState: String, apiKey: String)
            = client.getWeeklyWeatherForecast(cityNameAndState, apiKey)
}