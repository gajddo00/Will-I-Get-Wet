package com.example.willigetwet.ui.home

import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.bumptech.glide.Glide
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

    val weatherInfo = liveData(Dispatchers.IO) {
        val retrievedWeather = repository.getWeeklyWeatherForecast("Olomouc,CZ", NetworkConstants.apiKey)
        val weatherResponse: WeatherResponse? = retrievedWeather.execute().body()
        emit(weatherResponse)
    }

    fun downloadIcon(iconValue: String) {
        var url = "https://openweathermap.org/img/wn/$iconValue@2x.png"
    }
}