package com.example.madpt.API.member

import com.google.gson.annotations.SerializedName

data class MemberProfile(
    @SerializedName("name")
    val name: String,
    @SerializedName("weight")
    val weight: Double,
    @SerializedName("height")
    val height: Double,
)