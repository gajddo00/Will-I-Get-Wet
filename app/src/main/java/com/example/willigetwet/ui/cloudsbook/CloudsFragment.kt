package com.example.willigetwet.ui.cloudsbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import com.example.willigetwet.R
import com.example.willigetwet.service.CloudService
import java.util.*

class CloudsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_clouds, container, false)
        val clouds = CloudService.getClouds(resources)


        for (i in 0..12) {
            view.findViewWithTag<CardView>(i.toString()).setOnClickListener {
                Navigation.findNavController(view)
                    .navigate(CloudsFragmentDirections.navToDetail(tag = i, title = clouds[i].name.toUpperCase(
                        Locale.ROOT)))
            }
        }

        return view
    }
}