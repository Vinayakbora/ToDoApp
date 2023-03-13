package com.example.todoapp.ui

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.example.todoapp.R
import com.example.todoapp.data.LoginPreference

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val title = findViewById<TextView>(R.id.appTitle)
        val subHeading = findViewById<TextView>(R.id.subheading)
        val tick = findViewById<ImageView>(R.id.tick)
        val loginStatus = LoginPreference(this).getLoginStatus()
        val handler = Handler(Looper.getMainLooper())

        handler.postDelayed({
            subHeading.visibility = View.VISIBLE
        }, 1001)

        handler.postDelayed({
            val avd = AppCompatResources.getDrawable(this, R.drawable.done_tick) as AnimatedVectorDrawable
            tick.setImageDrawable(avd)
            avd.start()
        }, 6500)

        handler.postDelayed({
            val animator = ValueAnimator.ofFloat(0.0f, 1.0f)
            animator.duration = 1500
            animator.repeatCount = 2
            animator.addUpdateListener { valueAnimator ->
                val alpha = valueAnimator.animatedValue as Float
                title.alpha = alpha
            }
            animator.start()
        }, 4000)

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
            }, 1000)
        }
        subHeading.animateText(subHeading.text.toString())


        handler.postDelayed({
            val intent: Intent
            if(loginStatus){
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                intent = Intent(this, ViewPagerActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 6800)
    }
}