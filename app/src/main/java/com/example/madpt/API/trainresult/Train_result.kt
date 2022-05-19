package com.example.madpt.API.trainresult

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class Train_result(
    @SerializedName("breaktime")
    var breaktime: Int,
    @SerializedName("records")
    val result: ArrayList<Records>
)

data class Records(
    @SerializedName("exercise_id")
    val exercise: Long,
    @SerializedName("start_time")
    val startTime:Long,
    @SerializedName("end_time")
    val endTime:Long,
    @SerializedName("real_time")
    val realTime: Int,
    @SerializedName("reps")
    val reps: Int,
    @SerializedName("sets")
    val sets: Int,
    @SerializedName("avg_score")
    val avg_score: Double,
)