package com.example.willigetwet.ui.location

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.willigetwet.R
import kotlinx.android.synthetic.main.toolbar.*


class LocationFragment : Fragment(), LocationDelegate {

    private lateinit var locationViewModel: LocationViewModel
    private lateinit var adapter: CitiesRecyclerViewAdapter
    var sharedPrefs: SharedPreferences? = null
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPrefs = context?.getSharedPreferences(
            "tItYaxxELT",
            Context.MODE_PRIVATE
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_location, container, false)

        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)

        locationViewModel.getCities()
        locationViewModel.cities.observe(viewLifecycleOwner, Observer {
            adapter.setCities(it)
        })

        setupRecyclerView()
        setState()
        listenToSearch()
        return rootView
    }

    private fun setState() {
        val locationSwitch = rootView.findViewById<SwitchCompat>(R.id.location_switch)
        sharedPrefs?.getBoolean("show_location", false)?.let{
            locationSwitch.isChecked = it
            setView(it)
        }

        locationSwitch.setOnCheckedChangeListener{ _, isChecked ->
            with(sharedPrefs?.edit()) {
                this?.putBoolean("show_location", isChecked)
                this?.apply()
            }

            if (!isChecked) {
                with(sharedPrefs?.edit()) {
                    this?.putString("location_name", "")
                    this?.apply()
                }
            }

            setView(isChecked)
        }
    }

    private fun setView(isChecked: Boolean) {
        if (!isChecked) {
            rootView.findViewById<CardView>(R.id.search_card).visibility = View.INVISIBLE
            rootView.findViewById<RecyclerView>(R.id.cities_recycler_view).visibility = View.INVISIBLE
        } else {
            rootView.findViewById<CardView>(R.id.search_card).visibility = View.VISIBLE
            rootView.findViewById<RecyclerView>(R.id.cities_recycler_view).visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.cities_recycler_view)
        adapter = CitiesRecyclerViewAdapter(requireContext(), this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
            .also { it.orientation = LinearLayoutManager.VERTICAL}
    }

    private fun listenToSearch() {
        rootView.findViewById<SearchView>(R.id.location_text)
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false

                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText)
                    return false
                }
            })
    }

    override fun updateLocation(cityName: String) {
        with(sharedPrefs?.edit()) {
            this?.putString("location_name", cityName)
            this?.apply()
        }

        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(rootView.windowToken, 0)

        Navigation.findNavController(rootView).navigateUp()
    }

}
