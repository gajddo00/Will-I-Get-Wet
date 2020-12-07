package com.example.willigetwet.api

import com.example.willigetwet.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("forecast?")
    fun getWeeklyWeatherForecast(@Query("lat") latitude: Double,
                                 @Query("lon") longitude: Double,
                                  @Query("appid") apiKey: String): Call<WeatherResponse>

    @GET("forecast?")
    fun getWeeklyWeatherForecast(@Query("q") cityName: String,
                                 @Query("appid") apiKey: String): Call<WeatherResponse>
}