package com.example.willigetwet.ui.clouddetail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.willigetwet.R
import com.example.willigetwet.model.Cloud
import com.example.willigetwet.service.CloudService
import java.util.*

class CloudDetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var tag = 0
    private var cloud: Cloud? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_cloud_detail, container, false)

        arguments?.let {
            val args = it
            tag = CloudDetailFragmentArgs.fromBundle(args).tag
            cloud = CloudService.getClouds(resources)[tag]

            loadHeaderImage(view, CloudDetailFragmentArgs
                .fromBundle(args).title.toLowerCase(Locale.ROOT))

            view.findViewById<TextView>(R.id.cloud_info).text = cloud?.description
            view.findViewById<TextView>(R.id.cloud_rain).text = cloud?.rainProbability
        }
        return view
    }

    private fun loadHeaderImage(view: View, title: String) {
        val legitTitle = title
            .replace("-", "")
            .replace(" ", "")

        val image = context?.let { cntxt ->
            ContextCompat.getDrawable(cntxt ,resources.getIdentifier(
                legitTitle,
                "drawable",
                context?.packageName
            ))
        }
        Glide.with(view)
            .load(image)
            .centerCrop()
            .into(view.findViewById(R.id.header_image))
    }

}