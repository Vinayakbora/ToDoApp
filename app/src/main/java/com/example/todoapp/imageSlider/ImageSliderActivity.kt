package com.example.todoapp.imageSlider

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.todoapp.R
import com.example.todoapp.ui.SigningUpActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ImageSliderActivity : AppCompatActivity() {

    companion object {
        fun openImgSliderActivity(ctx: Context) {
            ctx.startActivity(Intent(ctx, ImageSliderActivity::class.java))
        }
    }

    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var skipButton: Button

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_slider)

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

        viewPager2 = findViewById(R.id.viewPager2)
        viewPager2.adapter = imageSliderAdapter

        tabLayout = findViewById(R.id.tabLayout)
        tabLayout.setBackgroundColor(android.R.color.transparent)
        TabLayoutMediator(tabLayout, viewPager2) { _, _ -> }.attach()

        skipButton = findViewById(R.id.buttonSkip)
        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val lastPosition = viewPager2.adapter?.itemCount?.minus(1) ?: 0
                if (position == lastPosition) {
                    skipButton.text = getString(R.string.finish)
                } else {
                    skipButton.text = getString(R.string.skip)
                }
            }
        })

        skipButton.setOnClickListener {
            SigningUpActivity.openSignInActivity(this)
            finish()
        }
    }
}