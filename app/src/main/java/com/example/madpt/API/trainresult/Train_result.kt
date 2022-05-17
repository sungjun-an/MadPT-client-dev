package com.example.madpt.API.trainresult

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class Train_result(
    @SerializedName("breaktime")
    val breaktime: Int,
    @SerializedName("records")
    val result: ArrayList<Records>
)

data class Records(
    @SerializedName("excercise_id")
    val excercise: Long,
    @SerializedName("start_time")
    val startTime:Timestamp,
    @SerializedName("end_time")
    val endTime:Timestamp,
    @SerializedName("reps")
    val reps: Int,
    @SerializedName("sets")
    val sets: Int,
    @SerializedName("avg_score")
    val avg_score: Double,
)