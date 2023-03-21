package com.example.todoapp.ui

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.todoapp.data.LoginPreference
import com.example.todoapp.R
import java.util.*

private const val minDateTime : Long = 1893000000000
class SigningUpActivity : AppCompatActivity() {

    companion object{
        fun openSignInActivity(ctx: Context){
            ctx.startActivity(Intent(ctx,SigningUpActivity::class.java))
        }
    }

    private var isNameValidated: Boolean = false
    private var isNumberValidated: Boolean = false
    private var isDateValidated: Boolean = false
    private var loginStatus: LoginPreference? = null
    private var validation: FormValidation? = null
    private lateinit var nameText: EditText
    private lateinit var numberText: EditText
    private lateinit var dobText: EditText
    private lateinit var signInButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signing_up)

        signInButton = findViewById(R.id.signInButton)
        nameText = findViewById(R.id.nameText)
        numberText = findViewById(R.id.numberText)
        dobText = findViewById(R.id.dobText)

        validation = FormValidation(this)
        loginStatus = LoginPreference(this)

        dobText.setOnClickListener{
            datePicker()
        }

        nameText.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if(!hasFocus){
                    isNameValidated = validation?.validateName(nameText.text.toString(),nameText) ?: false
                }
            }
        }

        numberText.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if(!hasFocus){
                    checkPhoneValidation()
                }
            }
        }

        signInButton.setOnClickListener{
            validation?.validateName(nameText.text.toString(),nameText)
            validation?.validatePhone(numberText.text.toString(),numberText)
            isDateValidated = validation?.validateDOB(dobText.text.toString(),dobText)?: false
            checkValidation()
        }
    }

    private fun checkPhoneValidation(){
        isNumberValidated = validation?.validatePhone(numberText.text.toString(),numberText) ?: false
    }
    private fun datePicker(){
        checkPhoneValidation()
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
        datePickerDialog.datePicker.minDate = c.timeInMillis - minDateTime
        datePickerDialog.show()
    }
    private fun checkValidation(){
        if(isNameValidated  && isNumberValidated && isDateValidated){
            loginStatus?.saveLoginStatus()
            MainActivity.openMainActivity(this)
            finish()
        }
    }
}