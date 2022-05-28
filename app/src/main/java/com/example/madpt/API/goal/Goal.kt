package com.example.madpt.API.goal

import com.google.gson.annotations.SerializedName

data class Goal(
    @SerializedName("diet_kcal")
    val diet_kcal: Double,
    @SerializedName("exercise_kcal")
    val exercise_kcal: Double,
    @SerializedName("weight")
    val weight: Double
)
