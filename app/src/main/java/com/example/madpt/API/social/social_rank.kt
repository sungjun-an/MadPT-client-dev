package com.example.madpt.API.social

import com.example.madpt.FriendsData
import com.google.gson.annotations.SerializedName

data class SocialRank(
    @SerializedName("friends")
    val Rank:ArrayList<Friends>
)

data class Friends(
    @SerializedName("name")
    val name:String,
    @SerializedName("burned_kcal")
    val somo_kcal:Double
)

var FriendsDataList = ArrayList<Friends>()
