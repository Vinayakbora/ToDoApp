package com.example.todoapp

import android.content.Context
import android.content.Intent
import android.widget.EditText
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
    private var dobFlag: Int = 0

    init {
        this.ctx = ctx
    }

    fun validateName(name: String, value: EditText){
        if (name.isNotEmpty()) {
            if (Pattern.matches(nameRegex, name)) {
                nameFlag++
                checkValidation()
            } else {
                value.error = "Please enter a valid Name"
            }
        } else {
            value.error = "Name cannot be empty"
        }
    }

    fun validatePhone(phoneNumber: String, value: EditText){
        if (phoneNumber.isNotEmpty()) {
            if (Pattern.matches(phoneRegex, phoneNumber)) {
                phoneFlag++
                checkValidation()
            } else {
                value.error = "Please enter a valid Phone number"
            }
        } else {
            value.error = "Mobile Number cannot be empty"
        }
    }

    fun validateDOB(dob: String,value: EditText){
        if (dob.isNotEmpty()) {
            dobFlag++
            checkValidation()
        } else {
            value.error = "D.O.B cannot be empty"
        }
    }

    private fun checkValidation(){
        if(nameFlag >= 1 && phoneFlag>=1 && dobFlag>=1){
            Toast.makeText(ctx, "Logged In Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(ctx, MainActivity::class.java)
            startActivity(ctx, intent, null)
            loginStatus.saveLoginStatus()
        }
    }
}