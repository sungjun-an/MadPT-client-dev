package com.example.madpt

import java.io.Serializable
import android.content.Intent

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

val FoodList = ArrayList<FoodData>()

fun sampleFoodList(){

    FoodList.add(FoodData("제육볶음", 1, "성환식품", 100.0,100.0,30.0,30.0,30.0))
    FoodList.add(FoodData("제육덮밥", 2, "성준식품", 200.0,150.0,40.0,40.0,40.0))
    FoodList.add(FoodData("김치", 3, "재현식품", 150.0,250.0,40.0,20.0,10.0))
    FoodList.add(FoodData("불닭볶음면", 4, "기렴식품", 500.0,300.0,60.0,20.0,30.0))
    FoodList.add(FoodData("돈까스", 5, "정락식품", 300.0,200.0,10.0,100.0,40.0))
    FoodList.add(FoodData("비빔냉면", 6, "", 300.0,200.0,10.0,100.0,40.0))
}

val AddFoodList = ArrayList<ModifyFoodData>()










