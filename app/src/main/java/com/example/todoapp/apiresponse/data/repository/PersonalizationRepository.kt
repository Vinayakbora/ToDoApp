package com.example.todoapp.apiresponse.data.repository

import android.util.Log
import com.example.todoapp.apiresponse.IPersonalization
import com.example.todoapp.apiresponse.data.model.ApiResponse
import javax.inject.Inject

class PersonalizationRepository @Inject constructor(
    private val apiService: IPersonalization,
) {
    suspend fun getData(): ApiResponse? {
        return try {
            val response = apiService.getData()
            if (response.isSuccessful) {
                response.body()
            } else {
                Log.e("Data Error", "Data Not Found")
                ApiResponse(success = false, message = "Error Code ${response.code()}")
            }
        } catch (e: Exception) {
            Log.e("Connection Error", e.toString())
            ApiResponse(success = false, message = "Connection Error")
        }
    }
}

