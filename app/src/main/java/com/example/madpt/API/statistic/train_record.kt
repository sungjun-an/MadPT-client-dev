package com.example.madpt.API.statistic

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class TrainRecord(
    @SerializedName("record_list")
    val record_list: ArrayList<TrainRecordList>
)

data class TrainRecordList(
    @SerializedName("exercise_name")
    val exercise_name: String,
    @SerializedName("start_time")
    val start_time: Long,
    @SerializedName("sets")
    val sets: Int,
    @SerializedName("reps")
    val reps: Int,
    @SerializedName("kcal")
    val kcal: Double
)
