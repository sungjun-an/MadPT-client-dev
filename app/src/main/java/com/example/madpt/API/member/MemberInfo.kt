package com.example.madpt.API.member

import com.google.gson.annotations.SerializedName

data class MemberInfo(
    @SerializedName("name")
    val name: String,
    @SerializedName("weight")
    val weight: Double,
    @SerializedName("height")
    val height: Double,
    @SerializedName("gender_type")
    val gender_type: String
)