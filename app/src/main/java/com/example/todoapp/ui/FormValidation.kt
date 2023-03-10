package com.example.todoapp.ui

import android.content.Context
import android.widget.EditText
import com.example.todoapp.data.LoginPreference
import java.util.regex.Pattern

class FormValidation(ctx: Context) {

    private val ctx: Context
    private val loginStatus = LoginPreference(ctx)
    private val nameRegex = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*\$"
    private val phoneRegex = "^\\d{10}\$"

    init {
        this.ctx = ctx
    }

    fun validateName(name: String, value: EditText): Boolean{
        return if (name.isNotEmpty()) {
            if (Pattern.matches(nameRegex, name)) {
                loginStatus.saveName(name)
                loginStatus.saveLoginStatus()
                true
            } else {
                value.error = "Please enter a valid Name"
                false
            }
        } else {
            value.error = "Name cannot be empty"
            false
        }
    }

    fun validatePhone(phoneNumber: String, value: EditText): Boolean{
        return if (phoneNumber.isNotEmpty()) {
            if (Pattern.matches(phoneRegex, phoneNumber)) {
                true
            } else {
                value.error = "Please enter a valid Phone number"
                true
            }
        } else {
            value.error = "Mobile Number cannot be empty"
            true
        }
    }

    fun validateDOB(dob: String,value: EditText): Boolean{
        return if (dob.isNotEmpty()) {
            true
        } else {
            value.error = "D.O.B cannot be empty"
            false
        }
    }
}