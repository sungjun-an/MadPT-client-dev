package com.example.madpt.API.statistic

import com.google.gson.annotations.SerializedName

data class MonthData(
    @SerializedName("monthly_data")
    val monthData: ArrayList<MonthDataDateBy>
)

data class MonthDataDateBy(
    @SerializedName("daily_diet_kcal")
    val dailyDietKcal: Double,
    @SerializedName("daily_exercise_kcal")
    val dailyExerciseKcal: Double
)