package com.example.willigetwet.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object WeatherAPIProvider {
    private val gson: Gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
    val retrofit: WeatherService = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .callbackExecutor(Executors.newSingleThreadExecutor())
        .build()
        .create(WeatherService::class.java)
}