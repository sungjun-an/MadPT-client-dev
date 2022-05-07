package com.example.madpt.API

import com.example.madpt.API.diet.daily_diet
import com.example.madpt.API.food.Get_Food
import com.example.madpt.API.statistic.MonthData
import com.example.madpt.API.statistic.SummaryData
import com.example.madpt.API.statistic.TrainRecord
import com.example.madpt.API.trainresult.Train_result
import retrofit2.Call
import retrofit2.http.*
import java.sql.Timestamp

interface RetrofitService {
    @GET("food/food-list")
    fun getdata(@Query("food_name")name: String): Call<Get_Food>//완성
    
    @POST("diet")
    fun postDailyDiet(@Header("member_id") id: Long, @Body params: daily_diet): Call<PostResponse>

    @POST("record/result")
    fun postTrainResult(@Header("member_id") id: Long, @Body params: Train_result): Call<String>

    @GET("statistic/record")
    fun getTrainRecord(@Header("member_id") id: Long, @Query("start_day") start: Timestamp, @Query("end_day")end: Timestamp): Call<TrainRecord>

    @GET("statistic/calender")
    fun getCalenderRecord(@Header("member_id") id: Long, @Query("month") month: Int): Call<MonthData>

    @GET("statistic/day")
    fun getSummaryData(@Header("member_id") id: Long, @Query("timestamp") timestamp: Long): Call<SummaryData>
}