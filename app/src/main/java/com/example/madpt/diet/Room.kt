package com.example.madpt.diet

import java.io.Serializable

data class Room(
    val food_name: String,
    val food_id : Int,
    val maker_name : String?,
    val default_weight : Double,
    val default_kcal : Double,
    val default_carbohydrate : Double,
    val default_protein : Double,
    val default_fat : Double
) : Serializable{

}
