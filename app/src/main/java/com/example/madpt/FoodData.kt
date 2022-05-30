package com.example.madpt

import java.io.Serializable
import android.content.Intent
import com.example.madpt.API.diet.diet_list

data class FoodData(
    var food_name: String,
    val food_id : Int,
    val maker_name : String?,
    val default_weight : Double,
    var default_kcal : Double,
    var default_carbohydrate : Double,
    var default_protein : Double,
    var default_fat : Double
): Serializable{

}

data class ModifyFoodData(
    var food_name : String,
    val food_id: Int,
    val maker_name: String?,
    var weight : Double,
    var kcal : Double,

    var unit : String,
    var count : Int
) : Serializable{

}

data class AddFoodData(
    val food_name: String,
    val food_id : Int,
    val maker_name : String?,
    val default_kcal : Int?
)














