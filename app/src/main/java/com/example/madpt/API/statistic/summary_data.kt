package com.example.madpt.API.statistic

import com.example.madpt.API.goal.Goal
import com.google.gson.annotations.SerializedName

data class SummaryData(
    @SerializedName("burned_kcal")
    val exerciseKcal: Double = 0.0,
    @SerializedName("breakfast_kcal")
    val breakfastKcal: Double = 0.0,
    @SerializedName("lunch_kcal")
    val lunchKcal: Double = 0.0,
    @SerializedName("dinner_kcal")
    val dinnerKcal: Double = 0.0,
    @SerializedName("snack_kcal")
    val snackKcal: Double = 0.0,
    @SerializedName("goal_diet_kcal")
    val goaldietkcal: Double = 0.0,
    @SerializedName("goal_exercise_kcal")
    val goalexercisekcal : Double = 0.0,
    @SerializedName("goal_weight")
    val goalweight : Double = 0.0,
    @SerializedName("weight")
    val weight : Double = 0.0

)