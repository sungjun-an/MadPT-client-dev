package com.example.madpt.API

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("success")
    val success: Int,
    @SerializedName("error")
    val errorMessage: Int,
    @SerializedName("results")
    val results: ArrayList<String>
)
