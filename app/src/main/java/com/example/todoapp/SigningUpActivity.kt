package com.example.todoapp

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.util.*

class SigningUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signing_up)

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        val button = findViewById<Button>(R.id.signInButton)
        val nameText = findViewById<EditText>(R.id.nameText)
        val numberText = findViewById<EditText>(R.id.numberText)
        val dobText = findViewById<EditText>(R.id.dobText)
        val validation = FormValidation(this)

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

        button.setOnClickListener{
            validation.validateName(nameText.text.toString(),nameText)
            validation.validatePhone(numberText.text.toString(),numberText)
            validation.validateDOB(dobText.text.toString(),dobText)
        }
    }
}