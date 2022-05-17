package com.example.madpt.API.statistic

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.time.Instant
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

data class MonthData(
    @SerializedName("monthly_data")
    var monthData: ArrayList<MonthDataDateBy>
)

@kotlinx.parcelize.Parcelize
data class MonthDataDateBy(
    @SerializedName("date")
    val date: Long,
    @SerializedName("daily_diet_kcal")
    val dailyDietKcal: Double,
    @SerializedName("daily_exercise_kcal")
    val dailyExerciseKcal: Double
): Parcelable
