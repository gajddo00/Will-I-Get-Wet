package com.example.willigetwet.ui.location

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.willigetwet.model.CityInfo
import com.example.willigetwet.model.WeatherResponse
import com.example.willigetwet.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LocationViewModel(application: Application): AndroidViewModel(application) {

    private val repository: WeatherRepository = WeatherRepository(application)
    var cities: MutableLiveData<List<CityInfo>> = MutableLiveData()

    fun getCities() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                cities.postValue(repository.getCityNames())
                print("here")
            } catch (e: Exception) {
                print(e)
            }
        }
    }
}