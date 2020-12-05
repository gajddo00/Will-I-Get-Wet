package cs.jejda.start.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.willigetwet.R
import com.example.willigetwet.model.WeatherForecast


class ForecastRecyclerViewAdapter(activity: FragmentActivity) : RecyclerView.Adapter<ForecastRecyclerViewAdapter.ViewHolder>() {

    private var forecasts = mutableListOf<WeatherForecast>()
    private var activity: FragmentActivity = activity

    fun setForecasts(items: List<WeatherForecast>) {
        forecasts = items.toMutableList()
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var day: TextView = view.findViewById(R.id.day)
        var temperature: TextView = view.findViewById(R.id.temperature)
        var description: TextView = view.findViewById(R.id.description)
        var icon: ImageView = view.findViewById(R.id.icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_cell, parent, false)
        return ViewHolder(layout)
    }

    private fun loadWeatherIcon(imageView: ImageView, iconValue: String) {
        val url = "https://openweathermap.org/img/wn/$iconValue@4x.png"
        Glide.with(activity)
            .load(url)
            .fitCenter()
            .into(imageView)
    }

    override fun getItemCount(): Int = forecasts.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.day.text = "SAT"
        holder.temperature.text = (forecasts[position].main?.temp?.minus(273.15))?.toInt()
            .toString() + " °C"
        holder.description.text = forecasts[position].weather?.get(0)?.description
        loadWeatherIcon(holder.icon, forecasts[position].weather?.get(0)?.icon!!)
    }

}