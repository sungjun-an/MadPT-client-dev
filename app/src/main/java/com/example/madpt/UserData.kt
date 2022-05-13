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

var FriendsDataList = ArrayList<FriendsData>()

fun inputTestFriendsData(){
    FriendsDataList.add(FriendsData("김성환",1000))
    FriendsDataList.add(FriendsData("김정락",500))
    FriendsDataList.add(FriendsData("김재현",100))
    FriendsDataList.add(FriendsData("안성준",50))
    FriendsDataList.add(FriendsData("문기렴",10))
}
