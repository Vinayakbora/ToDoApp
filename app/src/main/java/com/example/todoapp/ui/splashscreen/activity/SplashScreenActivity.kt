package com.example.todoapp.ui.splashscreen.activity

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import com.example.todoapp.R
import com.example.todoapp.databinding.SplashScreenBinding
import com.example.todoapp.ui.*
import com.example.todoapp.ui.home.activity.TaskActivity
import com.example.todoapp.ui.onBoardingViewPager.activity.OnBoardingViewPagerActivity
import com.example.todoapp.utils.LoginPreference

private const val splashDuration: Long = 6500
private const val subHeadingDelay: Long = 3500
private const val titleDelay: Long = 100
private const val tickAnimationDelay: Long = 6200
private const val subHeadingVisibilityDelay: Long = 3501
private const val titleVisibilityDelay: Long = 310
private const val bounceAnimDuration: Long = 3000

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: SplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.splash_screen)

        val loginStatus = LoginPreference(this).getLoginStatus()
        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed({
            binding.appTitle.visibility = View.VISIBLE
        }, titleVisibilityDelay)

        handler.postDelayed({
            binding.subheading.visibility = View.VISIBLE
        }, subHeadingVisibilityDelay)

        handler.postDelayed({
            val avd = AppCompatResources.getDrawable(
                this,
                R.drawable.done_tick
            ) as AnimatedVectorDrawable
            binding.tick.setImageDrawable(avd)
            avd.start()
        }, tickAnimationDelay)

        handler.postDelayed({
            val bounceAnim =
                ObjectAnimator.ofFloat(binding.appTitle, "translationY", -200f, 100f, 500f)
            bounceAnim.duration = bounceAnimDuration
            bounceAnim.interpolator = BounceInterpolator()
            bounceAnim.start()
        }, titleDelay)

        fun TextView.animateText(text: String) {
            var i = 0
            handler.postDelayed(object : Runnable {
                override fun run() {
                    if (i <= text.length) {
                        val str = text.substring(0, i)
                        setText(str)
                        i++
                        handler.postDelayed(this, 75)
                    }
                }
            }, subHeadingDelay)
        }
        binding.subheading.animateText( binding.subheading.text.toString())

        handler.postDelayed({
            if (loginStatus) {
                TaskActivity.openMainActivity(this)
                finish()
            } else {
                OnBoardingViewPagerActivity.openImgSliderActivity(this)
                finish()
            }
        }, splashDuration)
    }
}