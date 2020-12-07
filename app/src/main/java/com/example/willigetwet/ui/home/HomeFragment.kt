package com.example.willigetwet.ui.home

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.willigetwet.R
import com.example.willigetwet.databinding.FragmentHomeBinding
import com.example.willigetwet.model.WeatherForecast
import com.example.willigetwet.model.WeatherResponse
import com.example.willigetwet.utility.WeatherDataProcessor
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import cs.jejda.start.adapters.ForecastRecyclerViewAdapter
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*

class HomeFragment : Fragment(), HomeViewModelDelegate {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private var weatherDataProcessor = WeatherDataProcessor()
    private var forecastRecyclerView: ForecastRecyclerViewAdapter? = null
    private val LOCATION_REQUEST = 111
    private var refreshLayout: SwipeRefreshLayout? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var sharedPrefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = context?.getSharedPreferences(
            "tItYaxxELT",
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.delegate = this
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.bind(root)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        initializeViewContent()
        getWeatherData()

        refreshLayout = root?.findViewById(R.id.refreshLayout)
        refreshLayout?.setOnRefreshListener {
            getWeatherData()
        }

        requireActivity().location.setOnClickListener {
             Navigation.findNavController(root)
                 .navigate(R.id.nav_to_location)
        }

        return binding.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_REQUEST && grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getWeatherData()
        } else {
            Toast.makeText(requireActivity(), "Location permission denied. App will not show current weather.",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun getWeatherData() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST)
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                sharedPrefs?.getBoolean("show_location", false)?.let{ checked ->
                    if (checked) {
                        sharedPrefs?.getString("location_name", "")?.let { city ->
                            homeViewModel.getForecastsByCityName(city)
                        }
                    } else if (!checked && location != null) {
                        homeViewModel.getForecasts(location)
                    } else { }
                }
            }

            fusedLocationClient.lastLocation.addOnFailureListener {
                Toast.makeText(requireActivity(), "Could not get location.",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initializeViewContent() {
        homeViewModel.weatherForecast.observe(viewLifecycleOwner, Observer {
            it?.let {
                val currentWeather = it.list?.get(0)
                it.list?.toMutableList()?.let {
                    val forecats = weatherDataProcessor.getForecastForNextFiveDays(it)
                    val recyclerView = view?.findViewById<RecyclerView>(R.id.forecast_recyclerview)
                    forecastRecyclerView = ForecastRecyclerViewAdapter(activity = requireActivity())
                    val layoutManager =  LinearLayoutManager(context)
                    layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                    recyclerView?.adapter = forecastRecyclerView
                    recyclerView?.layoutManager = layoutManager
                    forecastRecyclerView?.setForecasts(forecats)
                }

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

                refreshLayout?.isRefreshing = false
                requireActivity().toolbar_title.text = it.city?.name
            }
        })
    }

    override fun onResume() {
        super.onResume()
        requireActivity().toolbar_title.visibility = View.VISIBLE
        requireActivity().location.visibility = View.VISIBLE

        sharedPrefs?.getBoolean("show_location", false)?.let { checked ->
            sharedPrefs?.getString("location_name", "")?.let { city ->
                if (checked && city == "") {
                    with(sharedPrefs?.edit()) {
                        this?.putBoolean("show_location", false)
                        this?.apply()
                    }
                }
            }
        }
        getWeatherData()
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
        if (currentWeather?.rain?.rainValue == null) {
            binding.weatherRain.text = "n/a"
        } else {
            binding.weatherRain.text = currentWeather.rain?.rainValue.toString().plus("mm")
        }
    }

    private fun setSunriseTime(currentWeather: WeatherResponse?) {
        val formattedDate = utcToDate(currentWeather?.city?.sunrise!!)
        binding.weatherSunrise.text = daytimeToString(formattedDate, TIMEOFDAY.AM)

    }

    private fun setSunsetTime(currentWeather: WeatherResponse?) {
        val formattedDate = utcToDate(currentWeather?.city?.sunset!!)
        binding.weatherSunset.text = daytimeToString(formattedDate, TIMEOFDAY.PM)
    }

    private fun daytimeToString(formattedDate: Calendar, timeOfDay: TIMEOFDAY): String {
        var hour = formattedDate.get(Calendar.HOUR)
        var minutes = formattedDate.get(Calendar.MINUTE).toString()

        if (minutes.length == 1)  minutes = "0$minutes"
        if (timeOfDay == TIMEOFDAY.PM) hour += 12

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

    enum class TIMEOFDAY {
        PM, AM
    }

    override fun notifyRequestFailed() {
        requireActivity().runOnUiThread {
            Toast.makeText(requireActivity(), "Loading failed. Please check your internet connection.",
                Toast.LENGTH_LONG).show();
        }
    }

    override fun onPause() {
        super.onPause()
        requireActivity().toolbar_title.visibility = View.GONE
        requireActivity().location.visibility = View.GONE
    }
}
