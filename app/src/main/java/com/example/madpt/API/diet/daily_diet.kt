package com.example.madpt.API.diet

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

val AddFoodList = ArrayList<diet_list>()

data class daily_diet(
    @SerializedName("date")
    val date : Long,
    @SerializedName("diet_type")
    val diet_type : String,
    @SerializedName("simple_total_kcal")
    val simple_total_kcal : Double,
    @SerializedName("diet_list")
    val diet_list: ArrayList<diet_list>
)

data class diet_list(
    @SerializedName("food_id")
    val food_id : Long,
    @SerializedName("food_name")
    val food_name : String,
    @SerializedName("diet_kcal")
    val diet_kcal : Double,
    @SerializedName("weight")
    val weight : Double,
    @SerializedName("count")
    val count : Int,
    @SerializedName("unit")
    val unit : String,
    @SerializedName("is_custom")
    val is_custom : Boolean = false
)
