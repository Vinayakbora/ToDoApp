package com.example.todoapp

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.core.content.edit

class LoginPreference(context: Context) {

    private val loginStatus = "loginFlag"
    private val context: Context
    private val sharedPrefs: SharedPreferences

    init {
        this.context = context
        sharedPrefs = context.getSharedPreferences("flag", 0)
    }

     fun saveLoginStatus(){
        sharedPrefs.edit{
            putBoolean(loginStatus, true)
        }
    }

    fun getLoginStatus(): Boolean{
        return sharedPrefs.getBoolean(loginStatus,false)
    }

    fun deleteData(){
        val editor: SharedPreferences.Editor = sharedPrefs.edit()
        editor.clear()
        editor.apply()
        Toast.makeText(context, "Logged Out Successfully", Toast.LENGTH_SHORT).show()
    }

}