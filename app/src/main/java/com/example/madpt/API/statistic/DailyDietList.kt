package com.example.madpt.API.statistic

import com.google.gson.annotations.SerializedName

data class DailyDietList(
    @SerializedName("diet_type")
    val diet_type: String,
    @SerializedName("simple_diet_kcal")
    val simple_diet_kcal: Double,
    @SerializedName("diet_list_by_type")
    val diet_list_by_type: ArrayList<DietListByType>
)

data class DietListByType(
    @SerializedName("food_name")
    val food_name: String,
    @SerializedName("diet_kcal")
    val diet_kcal: Double,
    @SerializedName("weight")
    val weight: Double,
    @SerializedName("count")
    val count: Int,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("is_custom")
    val is_custom: Boolean,
    @SerializedName("food_data")
    val food_data: FoodData
)

data class FoodData(
    @SerializedName("default_carbohydrate")
    val default_car: Double,
    @SerializedName("default_protein")
    val default_pro: Double,
    @SerializedName("default_fat")
    val default_fat: Double
)