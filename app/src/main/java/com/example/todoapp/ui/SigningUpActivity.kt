package com.example.todoapp.ui

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.todoapp.data.LoginPreference
import com.example.todoapp.R
import com.example.todoapp.databinding.ActivitySigningUpBinding
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
    private lateinit var binding: ActivitySigningUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signing_up)

        validation = FormValidation(this)
        loginStatus = LoginPreference(this)

        binding.dobText.setOnClickListener{
            datePicker()
        }

        binding.nameText.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if(!hasFocus){
                    isNameValidated = validation?.validateName(binding.nameText.text.toString(),binding.nameText) ?: false
                }
            }
        }

        binding.numberText.apply {
            setOnFocusChangeListener { _, hasFocus ->
                if(!hasFocus){
                    checkPhoneValidation()
                }
            }
        }

        binding.signInButton.setOnClickListener{
            validation?.validateName(binding.nameText.text.toString(),binding.nameText)
            validation?.validatePhone(binding.numberText.text.toString(),binding.numberText)
            isDateValidated = validation?.validateDOB( binding.dobText.text.toString(), binding.dobText)?: false
            checkValidation()
        }
    }

    private fun checkPhoneValidation(){
        isNumberValidated = validation?.validatePhone(binding.numberText.text.toString(),binding.numberText) ?: false
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
                binding.dobText.setText(dat)
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