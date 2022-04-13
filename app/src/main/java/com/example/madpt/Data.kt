package com.example.madpt

data class AddDiet(
    val timestape: Int,
    val user_id: Int,
    val maker_name: String?,
    val type: Int,
    val weight: Int,
    val count: Int,
    val unit: String,
    val kcal: Int
    )

data class User(
    val user_id : Int,
    var eat_kcal : Int?,
    var move_kcal : Int?,
    var user_weight : Double?,
    var user_Goal_weight : Int?,
    var user_height : Double
)

var user = User(user_id = 1, 1, 2, 0.0, 0, 180.2)
val dietType = arrayListOf<AddDiet>()
val dietType1 = AddDiet(
    user_id = 1,
    count = 1,
    maker_name = "안녕",
    timestape = 1,
    type = 0,
    unit = "인분",
    weight = 9,
    kcal = 0
)
