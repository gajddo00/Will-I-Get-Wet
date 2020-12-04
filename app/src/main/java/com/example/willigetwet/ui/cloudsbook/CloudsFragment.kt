package com.example.willigetwet.ui.cloudsbook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.willigetwet.R
import com.example.willigetwet.model.Cloud
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class CloudsFragment : Fragment() {

    var clouds: List<Cloud> = listOf<Cloud>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun getClouds() {
        val filePath = resources.openRawResource(R.raw.data).toString()
        val json: String = File(filePath).readText(Charsets.UTF_8)
        val sType = object : TypeToken<List<Cloud>>() { }.type
        clouds = Gson().fromJson<List<Cloud>>(json, sType)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_clouds, container, false)
        getClouds()

        return view
    }
}