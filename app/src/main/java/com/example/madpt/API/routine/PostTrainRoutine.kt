package com.example.madpt.API.routine

import com.google.gson.annotations.SerializedName

data class PostTrainRoutine(
    @SerializedName("routine_name")
    val routine_title: String,
    @SerializedName("date")
    val date: Long,
    @SerializedName("breaktime")
    val breaktime: Int,
    @SerializedName("exercise_list")
    val exercise_list: ArrayList<ExerciseList>
)

data class ExerciseList(
    @SerializedName("exercise_id")
    val exercise_id: Long,
    @SerializedName("reps")
    val reps: Int,
    @SerializedName("sets")
    val sets: Int
)