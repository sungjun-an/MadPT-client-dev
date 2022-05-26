package com.example.madpt.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClass {
//    private const val baseUrl = "http://3.36.84.0:32113/"
    private const val baseUrl = "http://3.34.84.136:8080/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(RetrofitService::class.java)!!
}