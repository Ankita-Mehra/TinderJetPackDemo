package com.softradix.tinderjetpackdemo.retrofit

import com.softradix.tinderjetpackdemo.modelClass.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.inject.Singleton


@Singleton
interface ApiInterface {
    @Headers("Accept: " + "application/json")
    @POST("auth/login")
    suspend fun login(@Body request: HashMap<String, String>): SignUpResponse
}