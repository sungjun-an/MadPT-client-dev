package com.example.madpt.API

import com.example.madpt.API.diet.daily_diet
import com.example.madpt.API.food.Get_Food
import com.example.madpt.API.goal.Goal
import com.example.madpt.API.member.MemberInfo
import com.example.madpt.API.member.MemberWeight
import com.example.madpt.API.social.SocialRank
import com.example.madpt.API.statistic.MonthData
import com.example.madpt.API.statistic.SummaryData
import com.example.madpt.API.statistic.TrainRecord
import com.example.madpt.API.routine.GetTrainRoutine
import com.example.madpt.API.routine.PostTrainRoutine
import com.example.madpt.API.statistic.*
import com.example.madpt.API.trainresult.Train_result
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @GET("food/food-list")
    fun getdata(@Query("food_name")name: String): Call<Get_Food>//완성
    
    @POST("diet")
    fun postDailyDiet(@Header("member_id") id: Long, @Body params: daily_diet): Call<PostResponse>//완성

    @POST("member/sign-up")
    fun postMember(@Header("member_id") id:Long, @Body params: MemberInfo): Call<PostResponse>//완성

    @GET("member/login")
    fun postLogin(@Header("member_id")id: Long): Call<String>//완성

    @POST("member/update-weight")
    fun postWeight(@Header("member_id")id: Long, @Body params: MemberWeight): Call<String>

    @POST("goal")
    fun postGoal(@Header("member_id")id: Long, @Body params: Goal): Call<PostResponse> //명세 완료

    @POST("routine")
    fun postTrainRoutine(@Header("member_id")id: Long, @Body params: PostTrainRoutine): Call<PostResponse>//명세 완료

    @GET("routine")
    fun getTrainRoutine(@Header("member_id")id: Long): Call<GetTrainRoutine>

    @POST("record/result")
    fun postTrainResult(@Header("member_id") id: Long, @Body params: Train_result): Call<PostResponse>//명세 완료

    @GET("statistic/record")
    fun getTrainRecord(@Header("member_id") id: Long, @Query("date") start: Long): Call<TrainRecord>//명세 완료

    @GET("statistic/calendar")
    fun getCalenderRecord(@Header("member_id") id: Long, @Query("date") month: Long): Call<MonthData>//명세 완료

    @GET("statistic/day-summary")
    fun getSummaryData(@Header("member_id") id: Long, @Query("date") date: Long): Call<SummaryData>//명세 완료

    @GET("statistic/diet")
    fun getDailyDietList(@Header("member_id") id: Long, @Query("date") timestamp: Long): Call<DailyDietStatistic>//명세 완료

    @GET("social/rank")
    fun getSocialRank(@Query("date") timestamp: Long):Call<SocialRank>

}