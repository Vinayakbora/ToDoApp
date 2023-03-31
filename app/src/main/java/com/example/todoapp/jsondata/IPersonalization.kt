package com.example.todoapp.jsondata

import com.example.todoapp.jsondata.data.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface IPersonalization {
    @GET("content/dam/insurance-mall/homepage/homepage-bfl/android/android_homepage_bfl.json")
    suspend fun getData(): Response<ApiResponse>
}