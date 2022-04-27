package com.example.madpt.API

import com.example.madpt.API.food.Get_Food
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("food/list-dev")
    fun getdata(@Query("food_name")name: String): Call<Get_Food>
}