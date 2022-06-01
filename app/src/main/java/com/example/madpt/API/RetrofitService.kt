package com.example.madpt.API

import com.example.madpt.API.diet.daily_diet
import com.example.madpt.API.food.GetMemberProfile
import com.example.madpt.API.food.Get_Food
import com.example.madpt.API.goal.Goal
import com.example.madpt.API.member.MemberInfo
import com.example.madpt.API.member.MemberProfile
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
    @GET("get/food/food-list")
    fun getdata(@Query("food_name")name: String): Call<Get_Food>//완성
    
    @POST("post/diet")
    fun postDailyDiet(@Header("Member-Id") id: Long, @Body params: daily_diet): Call<PostResponse>//완성

    @POST("post/member/sign-up")
    fun postMember(@Header("Member-Id") id:Long, @Body params: MemberInfo): Call<PostResponse>//완성

    @GET("get/member/login")
    fun postLogin(@Header("Member-Id")id: Long): Call<String>//완성

    @POST("post/member/update-weight")
    fun postWeight(@Header("Member-Id")id: Long, @Body params: MemberWeight): Call<String>

    @POST("post/goal")
    fun postGoal(@Header("Member-Id")id: Long, @Body params: Goal): Call<PostResponse> //명세 완료

    @POST("post/routine")
    fun postTrainRoutine(@Header("Member-Id")id: Long, @Body params: PostTrainRoutine): Call<PostResponse>//명세 완료

    @GET("get/routine")
    fun getTrainRoutine(@Header("Member-Id")id: Long): Call<GetTrainRoutine>

    @POST("post/record/result")
    fun postTrainResult(@Header("Member-Id") id: Long, @Body params: Train_result): Call<PostResponse>//명세 완료

    @GET("get/statistic/record")
    fun getTrainRecord(@Header("Member-Id") id: Long, @Query("date") start: Long): Call<TrainRecord>//명세 완료

    @GET("get/statistic/calendar")
    fun getCalenderRecord(@Header("Member-Id") id: Long, @Query("date") month: Long): Call<MonthData>//명세 완료

    @GET("get/statistic/day-summary")
    fun getSummaryData(@Header("Member-Id") id: Long, @Query("date") date: Long): Call<SummaryData>//명세 완료

    @GET("get/statistic/diet")
    fun getDailyDietList(@Header("Member-Id") id: Long, @Query("date") timestamp: Long): Call<DailyDietStatistic>//명세 완료

    @GET("get/social/rank")
    fun getSocialRank(@Query("date") timestamp: Long):Call<SocialRank>

    @GET("get/member/info")
    fun getMemberInfo(@Header("Member-Id")id: Long): Call<MemberProfile>

}