package com.example.todoapp.ui.onBoardingViewPager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import com.example.todoapp.databinding.FragmentImageSliderBinding

class OnBoardingViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentImageSliderBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageSliderBinding.inflate(inflater, container, false)

        binding.imageViewLogo.setImageDrawable(
            arguments?.getInt("FragmentImage")?.let {
                AppCompatResources.getDrawable(requireContext(),it)
            })

        return binding.root
    }
}