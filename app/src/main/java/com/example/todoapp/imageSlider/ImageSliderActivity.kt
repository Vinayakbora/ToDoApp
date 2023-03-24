package com.example.todoapp.imageSlider

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivityImageSliderBinding
import com.example.todoapp.ui.SigningUpActivity
import com.google.android.material.tabs.TabLayoutMediator

class ImageSliderActivity : AppCompatActivity() {
    companion object {
        fun openImgSliderActivity(ctx: Context) {
            ctx.startActivity(Intent(ctx, ImageSliderActivity::class.java))
        }
    }

    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private lateinit var binding: ActivityImageSliderBinding

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_image_slider)

        imageSliderAdapter = ImageSliderAdapter(supportFragmentManager, lifecycle)

        val fragmentList = listOf(
            ImageSliderFragment().apply {
                arguments = Bundle()
                arguments?.putInt("FragmentImage", R.drawable.red)
            },
            ImageSliderFragment().apply {
                arguments = Bundle()
                arguments?.putInt("FragmentImage", R.drawable.blue)
            },
            ImageSliderFragment().apply {
                arguments = Bundle()
                arguments?.putInt("FragmentImage", R.drawable.green)
            }
        )

        imageSliderAdapter.addFragment(fragmentList)

        binding.viewPager2.adapter = imageSliderAdapter
        binding.tabLayout.setBackgroundColor(android.R.color.transparent)
        TabLayoutMediator( binding.tabLayout,  binding.viewPager2) { _, _ -> }.attach()

        binding.viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val lastPosition = binding.viewPager2.adapter?.itemCount?.minus(1) ?: 0
                if (position == lastPosition) {
                    binding.buttonSkip.text = getString(R.string.finish)
                } else {
                    binding.buttonSkip.text = getString(R.string.skip)
                }
            }
        })

        binding.buttonSkip.setOnClickListener {
            SigningUpActivity.openSignInActivity(this)
            finish()
        }
    }
}