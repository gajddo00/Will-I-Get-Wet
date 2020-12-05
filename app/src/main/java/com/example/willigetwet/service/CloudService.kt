package com.example.willigetwet.service

import com.example.willigetwet.R
import com.example.willigetwet.model.Cloud
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.content.res.Resources

object CloudService {

    fun getClouds(resources: Resources):  List<Cloud> {
        try {
            val json = resources.openRawResource(R.raw.data)
                .bufferedReader().use { it.readText() }
            val sType = object : TypeToken<List<Cloud>>() { }.type
             return Gson().fromJson<List<Cloud>>(json, sType)
        } catch (e: Exception) {
            println(e)
        }

        return listOf<Cloud>()
    }
}