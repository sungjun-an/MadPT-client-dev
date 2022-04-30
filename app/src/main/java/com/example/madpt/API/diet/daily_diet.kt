package com.example.madpt.API.diet

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp

data class daily_diet(
    @SerializedName("date")
    val date : Timestamp,
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
    @SerializedName("weight")
    val weight : Int,
    @SerializedName("count")
    val count : Int,
    @SerializedName("unit")
    val unit : String,
    @SerializedName("is_custom")
    val is_custom : Boolean
)
