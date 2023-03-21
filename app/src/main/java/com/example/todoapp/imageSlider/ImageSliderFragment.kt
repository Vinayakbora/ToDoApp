package com.example.todoapp.imageSlider

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.example.todoapp.R

class ImageSliderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image_slider, container, false)

        val viewPagerImage = view.findViewById<ImageView>(R.id.imageViewLogo)

        viewPagerImage.setImageDrawable(arguments?.getInt("FragmentImage")
            ?.let { AppCompatResources.getDrawable(requireContext(),it) })

        return view
    }
}