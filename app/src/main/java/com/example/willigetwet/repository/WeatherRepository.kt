package com.example.willigetwet.repository

import android.app.Application
import android.location.Location
import com.example.willigetwet.api.WeatherAPIProvider
import com.example.willigetwet.api.WeatherService
import cs.jejda.start.database.AppDatabase
import cs.jejda.start.database.CityInfoDao

class WeatherRepository(application: Application) {
    var client: WeatherService = WeatherAPIProvider.retrofit
    var dao: CityInfoDao? = AppDatabase.getAppDatabase(application)?.TODOItemDao()

    fun getWeeklyWeatherForecastByCoordinates(latitude: Double, longitude: Double, apiKey: String)
            = client.getWeeklyWeatherForecast(latitude, longitude, apiKey)

    fun getWeeklyWeatherForecastByCityName(cityName: String, apiKey: String)
            = client.getWeeklyWeatherForecast(cityName, apiKey)

    fun getCityNames() = dao?.getCitiesByName()
}