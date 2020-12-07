package com.example.willigetwet.ui.location

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.willigetwet.R
import com.example.willigetwet.model.CityInfo
import java.util.*
import kotlin.collections.ArrayList


interface LocationDelegate {
    fun updateLocation(cityName: String)
}

class CitiesRecyclerViewAdapter(
    private var context: Context,
    private var delegate: LocationDelegate
) : RecyclerView.Adapter<CitiesRecyclerViewAdapter.ViewHolder>(), Filterable {

    private var cities = mutableListOf<CityInfo>()
    private var filteredCities = mutableListOf<CityInfo>()

    fun setCities(items: List<CityInfo>) {
        cities = items.sortedBy { it.name }.toMutableList()
        filteredCities = cities
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.location_cell, parent, false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int = filteredCities.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = filteredCities[position].name
        holder.name.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Add location?")
            builder.setPositiveButton("Yes") { dialog, which ->
                delegate.updateLocation(filteredCities[position].name)
            }

            builder.setNegativeButton("No") { dialog, which ->

            }
            builder.show()
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filteredCities = cities
                } else {
                    val resultList = ArrayList<CityInfo>()
                    for (row in cities) {
                        if (row.name.toLowerCase(Locale.ROOT).startsWith(
                                charSearch.toLowerCase(
                                    Locale.ROOT
                                )
                            )
                        ) {
                            resultList.add(row)
                        }
                    }
                    filteredCities = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredCities
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredCities = (results?.values as List<CityInfo>).toMutableList()
                notifyDataSetChanged()
            }
        }
    }

}