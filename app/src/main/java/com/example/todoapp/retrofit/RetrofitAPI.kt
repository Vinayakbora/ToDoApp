package com.example.todoapp.retrofit

import retrofit2.Response
import retrofit2.http.GET

interface RetrofitAPI {
    @GET("content/dam/insurance-mall/homepage/homepage-bfl/android/android_homepage_bfl.json")
    suspend fun getData(): Response<ApiResponse>
}