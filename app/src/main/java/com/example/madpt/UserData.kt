package com.example.madpt

import java.time.LocalDateTime

data class UserData(
    var Date : LocalDateTime,
    val diet_type : String,
    var simple_total_kcal : Int,
    var diet_list : ArrayList<ModifyFoodData>
)

var userDietData = ArrayList<UserData>()

data class FriendsData(
    var name : String,
    var useKcal : Int
)


