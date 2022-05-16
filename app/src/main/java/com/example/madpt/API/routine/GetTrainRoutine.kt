package com.example.madpt.API.routine

import com.google.gson.annotations.SerializedName

data class GetTrainRoutine(
    @SerializedName("routine_list")
    val routine_list: ArrayList<PostTrainRoutine>
)