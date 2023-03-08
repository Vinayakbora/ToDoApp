package com.example.todoapp

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import java.util.regex.Pattern

class FormValidation(ctx: Context) {

    private val ctx: Context
    private val loginStatus = LoginPreference(ctx)
    private val nameRegex = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*\$"
    private val phoneRegex = "^\\d{10}\$"
    private var nameFlag: Int = 0
    private var phoneFlag: Int = 0

    init {
        this.ctx = ctx
    }

    fun validateName(name: String){
        if (name.isNotEmpty()) {
            if (Pattern.matches(nameRegex, name)) {
                nameFlag++
                checkValidation()
            } else {
                Toast.makeText(
                    ctx, "Please enter a valid Name", Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                ctx, "Name cannot be empty", Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun validatePhone(phoneNumber: String){
        if (phoneNumber.isNotEmpty()) {
            if (Pattern.matches(phoneRegex, phoneNumber)) {
                phoneFlag++
                checkValidation()
            } else {
                Toast.makeText(
                    ctx, "Please enter a valid Phone number", Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            Toast.makeText(
                ctx, "Mobile Number cannot be empty", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun checkValidation(){
        if(nameFlag >= 1 && phoneFlag>=1){
            Toast.makeText(ctx, "Logged In Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(ctx, MainActivity::class.java)
            startActivity(ctx, intent, null)
            loginStatus.saveLoginStatus()
        }
    }
}