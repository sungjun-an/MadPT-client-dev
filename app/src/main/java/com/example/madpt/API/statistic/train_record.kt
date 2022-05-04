package com.example.madpt.API.statistic

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class TrainRecord(
    @SerializedName("record_list")
    val record_list: ArrayList<TrainRecordList>
)

data class TrainRecordList(
    @SerializedName("exercise_id")
    val exercise_id: Int,
    @SerializedName("start_time")
    val start_time: Timestamp,
    @SerializedName("score")
    val score:Int,
    @SerializedName("count")
    val count:Int
)
