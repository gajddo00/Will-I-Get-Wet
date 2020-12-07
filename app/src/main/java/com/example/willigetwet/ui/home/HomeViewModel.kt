package com.example.willigetwet.ui.home

import android.app.Application
import android.location.Location
import androidx.lifecycle.*
import com.example.willigetwet.model.Weather
import com.example.willigetwet.model.WeatherForecast
import com.example.willigetwet.model.WeatherResponse
import com.example.willigetwet.repository.WeatherRepository
import com.example.willigetwet.utility.NetworkConstants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface HomeViewModelDelegate {
    fun notifyRequestFailed()
}

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: WeatherRepository = WeatherRepository(application)

    var weatherForecast: MutableLiveData<WeatherResponse> = MutableLiveData()
    var delegate: HomeViewModelDelegate? = null

    fun getForecasts(location: Location) {
        val retrievedWeather = repository.getWeeklyWeatherForecastByCoordinates(location.latitude,
            location.longitude, NetworkConstants.apiKey)
        retrievedWeather.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                val data: WeatherResponse = response.body() as WeatherResponse
                weatherForecast.postValue(data)
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                delegate?.notifyRequestFailed()
            }
        })
    }

    fun getForecastsByCityName(cityName: String) {
        val retrievedWeather = repository.getWeeklyWeatherForecastByCityName(cityName, NetworkConstants.apiKey)
        retrievedWeather.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                val data: WeatherResponse = response.body() as WeatherResponse
                weatherForecast.postValue(data)
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                delegate?.notifyRequestFailed()
            }
        })
    }
}