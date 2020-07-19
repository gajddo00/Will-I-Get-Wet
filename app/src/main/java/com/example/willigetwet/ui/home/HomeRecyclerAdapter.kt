package com.example.willigetwet.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.willigetwet.R
import com.example.willigetwet.model.WeatherForecast

class HomeRecyclerAdapter : RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>() {
    private var forecasts: Array<WeatherForecast> = arrayOf()

    fun setWeatherForecast(elements: Array<WeatherForecast>) {
        forecasts = elements
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var main: TextView = view.findViewById(R.id.weather_main)
        var description: TextView = view.findViewById(R.id.weather_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_list_element, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = forecasts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.main.text = forecasts[position].weather?.get(0)?.main ?: "No weather"
        holder.description.text = forecasts[position].weather?.get(0)?.description ?: "No description"
    }
}