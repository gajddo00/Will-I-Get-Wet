package com.example.willigetwet.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.willigetwet.R
import com.example.willigetwet.databinding.FragmentHomeBinding
import com.example.willigetwet.model.WeatherForecast
import com.example.willigetwet.model.WeatherResponse
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.time.hours

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(root)


        homeViewModel.weatherForecast.observe(viewLifecycleOwner, Observer {
            it.let {
                val currentWeather = it?.list?.get(0)

                setWeatherIcon(currentWeather)
                setWeatherTemperature(currentWeather)
                setWeatherDescription(currentWeather)
                setWeatherHumidity(currentWeather)
                setWeatherPressure(currentWeather)
                setWeatherClouds(currentWeather)
                setWeatherFeelsLike(currentWeather)
                setWeatherWind(currentWeather)
                setWeatherRain(currentWeather)
                setSunriseTime(it)
                setSunsetTime(it)
            }
        })

        return binding.root
    }

    private fun setWeatherTemperature(currentWeather: WeatherForecast?) {
        val tempInCelsius = kelvinToCelsius(currentWeather?.main?.temp!!)
        binding.weatherTemperature.text = tempInCelsius.toString().plus("°")
    }

    private fun setWeatherIcon(currentWeather: WeatherForecast?) {
        val iconValue = currentWeather?.weather?.get(0)?.icon
        loadWeatherIcon(binding.weatherIcon, iconValue!!)
    }

    private fun setWeatherDescription(currentWeather: WeatherForecast?) {
        binding.weatherDescription.text = currentWeather?.weather?.get(0)?.description?.toUpperCase(Locale.getDefault())
    }

    private fun setWeatherHumidity(currentWeather: WeatherForecast?) {
        binding.weatherHumidity.text = currentWeather?.main?.humidity?.toInt().toString().plus("%")
    }

    private fun setWeatherPressure(currentWeather: WeatherForecast?) {
        binding.weatherPressure.text = currentWeather?.main?.pressure?.toInt().toString().plus("hPa")
    }

    private fun setWeatherClouds(currentWeather: WeatherForecast?) {
        binding.weatherClouds.text = currentWeather?.clouds?.all?.toInt().toString().plus("%")
    }

    private fun setWeatherFeelsLike(currentWeather: WeatherForecast?) {
        val tempInCelsius = kelvinToCelsius(currentWeather?.main?.feelsLike!!)
        binding.weatherFeelsLike.text = tempInCelsius.toString().plus("°C")
    }

    private fun setWeatherWind(currentWeather: WeatherForecast?) {
        binding.weatherWindSpeed.text = currentWeather?.wind?.speed?.toString().plus("m/s")
    }

    private fun setWeatherRain(currentWeather: WeatherForecast?) {
        binding.weatherRain.text = currentWeather?.rain?.rainValue?.toString().plus("mm")
    }

    private fun setSunriseTime(currentWeather: WeatherResponse?) {
        val formattedDate = utcToDate(currentWeather?.city?.sunrise!!)
        binding.weatherSunrise.text = daytimeToString(formattedDate, TIMEOFDAY.AM)

    }

    private fun setSunsetTime(currentWeather: WeatherResponse?) {
        val formattedDate = utcToDate(currentWeather?.city?.sunset!!)
        binding.weatherSunset.text = daytimeToString(formattedDate, TIMEOFDAY.PM)
    }

    private fun daytimeToString(formattedDate: Calendar, timeofday: TIMEOFDAY): String {
        var hour = formattedDate.get(Calendar.HOUR)
        var minutes = formattedDate.get(Calendar.MINUTE).toString()

        if (minutes.length == 1)  minutes = "0$minutes"
        if (timeofday == TIMEOFDAY.PM) hour += 12

        return "$hour:$minutes"
    }

    private fun kelvinToCelsius(value: Float): Int = (value - 273.15).toInt()

    private fun utcToDate(timeInUtc: Long): Calendar {
        val date = Date(timeInUtc * 1000.toLong())
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }

    private fun loadWeatherIcon(imageView: ImageView, iconValue: String) {
        val url = "https://openweathermap.org/img/wn/$iconValue@4x.png"
        Glide.with(this)
            .load(url)
            .fitCenter()
            .into(imageView)
    }

    public enum class TIMEOFDAY {
        PM, AM
    }
}
