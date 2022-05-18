package com.example.madpt.API.statistic

import com.example.madpt.API.goal.Goal
import com.google.gson.annotations.SerializedName

data class SummaryData(
    @SerializedName("exercise_kcal")
    val exerciseKcal: Double,
    @SerializedName("breakfast_kcal")
    val breakfastKcal: Double,
    @SerializedName("lunch_kcal")
    val lunchKcal: Double,
    @SerializedName("dinner_kcal")
    val dinnerKcal: Double,
    @SerializedName("snack_kcal")
    val snackKcal: Double,
    @SerializedName("goal")
    val goal: Goal
)