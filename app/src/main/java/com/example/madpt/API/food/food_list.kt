package com.example.madpt.API.food

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.ArrayList

data class Get_Food(
    @SerializedName("food_list")
    var test: ArrayList<food_list>?=null
)

data class food_list(
    @SerializedName("food_name")
    val food_name: String,
    @SerializedName("food_id")
    val food_id: Long,
    @SerializedName("maker_name")
    val maker_name: String,
    @SerializedName("food_data")
    val food_data: foodData
): Serializable

data class foodData(
    @SerializedName("default_weight")
    val defaultWeight: Double,
    @SerializedName("default_kcal")
    val defaultKcal: Double,
    @SerializedName("default_carbohydrate")
    val defaultCarbohydrate: Double,
    @SerializedName("default_protein")
    val defaultProtein: Double,
    @SerializedName("default_fat")
    val defaultFat: Double
): Serializable

