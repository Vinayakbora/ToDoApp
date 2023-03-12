package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager.widget.ViewPager

class ViewPagerActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var skipButton: Button
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var imageList: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        skipButton = findViewById(R.id.skipBtn)
        viewPager = findViewById(R.id.idViewPager)


        imageList = ArrayList()
        imageList = imageList + R.drawable.yellow
        imageList = imageList + R.drawable.red
        imageList = imageList + R.drawable.blue
        imageList = imageList + R.drawable.green


        viewPagerAdapter = ViewPagerAdapter(this@ViewPagerActivity, imageList)
        viewPager.adapter = viewPagerAdapter


        skipButton.setOnClickListener{
            intent = Intent(this, SigningUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                // Will implement later
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // Will implement later
            }

            override fun onPageSelected(position: Int) {
                val lastPosition = viewPager.adapter?.count?.minus(1) ?: 0
                if (position == lastPosition) {
                    skipButton.text = getString(R.string.finish)
                }
                else{
                    skipButton.text = getString(R.string.skip)
                }
            }
        })
    }
}