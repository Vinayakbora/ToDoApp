package com.example.todoapp.ui

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.todoapp.data.LoginPreference
import com.example.todoapp.R
import java.util.*

class SigningUpActivity : AppCompatActivity() {

    private var isNameValidated: Boolean = false
    private var isNumberValidated: Boolean = false
    private var isDateValidated: Boolean = false
    private var loginStatus: LoginPreference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signing_up)

        val signInButton = findViewById<Button>(R.id.signInButton)
        val nameText = findViewById<EditText>(R.id.nameText)
        val numberText = findViewById<EditText>(R.id.numberText)
        val dobText = findViewById<EditText>(R.id.dobText)
        val validation = FormValidation(this)
        loginStatus = LoginPreference(this)

        dobText.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                { _, birthYear, monthOfYear, dayOfMonth ->
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + birthYear)
                    dobText.setText(dat)
                },
                year, month, day
            )
            datePickerDialog.datePicker.maxDate = c.timeInMillis
            datePickerDialog.datePicker.minDate = c.timeInMillis - 1893000000000
            datePickerDialog.show()
        }

        signInButton.setOnClickListener{
            isNameValidated = validation.validateName(nameText.text.toString(),nameText)
            isNumberValidated = validation.validatePhone(numberText.text.toString(),numberText)
            isDateValidated = validation.validateDOB(dobText.text.toString(),dobText)
            checkValidation()
        }
    }
    private fun checkValidation(){
        if(isNameValidated  && isNumberValidated && isDateValidated){
            Toast.makeText(this, "Logged In Successfully", Toast.LENGTH_SHORT).show()
            loginStatus?.saveLoginStatus()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}