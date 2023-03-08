package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logoutBtn: Button = findViewById(R.id.logoutBtn)
        val loginStatus = LoginPreference(this)

        logoutBtn.setOnClickListener{
            loginStatus.deleteData()
            val intent = Intent(this, SigningUpActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}