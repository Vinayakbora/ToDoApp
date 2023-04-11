package com.example.todoapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class LoginPreference(val context: Context) {

    private val loginStatus = "loginFlag"
    private val userName = "username"
    private val sharedPrefs: SharedPreferences = EncryptedSharedPreferences.create(
        "SHARED_PREFERENCES",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveLoginStatus() {
        sharedPrefs.edit {
            putBoolean(loginStatus, true)
        }
    }

    fun getLoginStatus(): Boolean {
        return sharedPrefs.getBoolean(loginStatus, false)
    }

    fun saveName(name: String) {
        sharedPrefs.edit {
            putString(userName, name)
        }
    }

    fun getName(): String {
        return sharedPrefs.getString(userName, "") ?: ""
    }

    fun deleteData() {
        val editor: SharedPreferences.Editor = sharedPrefs.edit()
        editor.clear()
        editor.apply()
    }
}