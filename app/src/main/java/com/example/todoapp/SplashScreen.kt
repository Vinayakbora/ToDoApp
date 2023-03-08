package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val loginStatus = LoginPreference(this).getLoginStatus()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
            val intent: Intent
            if(loginStatus){
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }
}