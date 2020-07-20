package com.example.willigetwet.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.willigetwet.model.WeatherResponse
import com.example.willigetwet.repository.WeatherRepository
import com.example.willigetwet.utility.NetworkConstants
import kotlinx.coroutines.Dispatchers

class HomeViewModel : ViewModel() {
    private val repository: WeatherRepository = WeatherRepository()

    /*private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }*/
//
//    private val _icon = MutableLiveData<Drawable>().apply {
//        value =
//    }

    val weatherForecast = liveData(Dispatchers.IO) {
        val retrievedWeather = repository.getWeeklyWeatherForecast("Olomouc,CZ", NetworkConstants.apiKey)
        val weatherResponse: WeatherResponse? = retrievedWeather.execute().body()
        emit(weatherResponse)
    }

}