package com.example.willigetwet.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.willigetwet.R
import com.example.willigetwet.model.WeatherForecast
import com.example.willigetwet.model.WeatherResponse
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: HomeRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val iconView = root.findViewById<ImageView>(R.id.weather_icon)
        val temperatureTextView = root.findViewById<TextView>(R.id.weather_temperature)
        val descriptionTextView = root.findViewById<TextView>(R.id.weather_description)

//        viewManager = LinearLayoutManager(context)
//        viewAdapter = HomeRecyclerAdapter()
//        recyclerView = root.findViewById<RecyclerView>(R.id.home_recycler_view).apply {
//            setHasFixedSize(true)
//            layoutManager = viewManager
//            adapter = viewAdapter
//        }

        homeViewModel.weatherInfo.observe(viewLifecycleOwner, Observer {
            it.let {
                val currentWeather = it?.list?.get(0)

                setWeatherIcon(currentWeather, iconView)
                setWeatherTemperature(currentWeather, temperatureTextView)
                setWeatherDescription(currentWeather, descriptionTextView)
            }
        })

        return root
    }

    private fun setWeatherTemperature(currentWeather: WeatherForecast?, temperatureTextview: TextView) {
        val tempInCelsius = kelvinToCelsius(currentWeather?.main?.temp!!)
        temperatureTextview.text = tempInCelsius.toString().plus("°")
    }

    private fun setWeatherIcon(currentWeather: WeatherForecast?, iconView: ImageView) {
        val iconValue = currentWeather?.weather?.get(0)?.icon
        loadWeatherIcon(iconView, iconValue!!)
    }

    private fun setWeatherDescription(currentWeather: WeatherForecast?, descriptionView: TextView) {
        descriptionView.text = currentWeather?.weather?.get(0)?.description?.toUpperCase(Locale.getDefault())
    }

    private fun kelvinToCelsius(value: Float): Int = (value - 273.15).toInt()

    private fun loadWeatherIcon(imageView: ImageView, iconValue: String) {
        val url = "https://openweathermap.org/img/wn/$iconValue@4x.png"
        Glide.with(this)
            .load(url)
            .fitCenter()
            .into(imageView)
    }
}
