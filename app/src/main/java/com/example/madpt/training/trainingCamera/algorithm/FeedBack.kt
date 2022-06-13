package com.example.madpt.training.trainingCamera.algorithm

import java.util.*
import kotlin.collections.ArrayList

class FeedBack {
    var exercise: Int
    var scoreList: ArrayList<Int>
    var feedBackMatrix: Array<Array<String>>

    init{
        this.exercise = -1
        this.scoreList = ArrayList()
        this.feedBackMatrix =  arrayOf(
            arrayOf("더 내려가", "더 내려가","엉덩이 내려"),
            arrayOf("더 내려가", "무릎 좀 더 뒤로", "허리 펴", "잘했어요"),
            arrayOf("더 내려가", "오른쪽 다리 세워", "왼쪽 다리 세워"),
            arrayOf("왼쪽 팔", "오른쪽 팔", "왼쪽 팔 더 굽혀", "오른쪽 팔 더 굽혀"),

            arrayOf("엉덩이 더 들어", "엉덩이 더 숙여", "무릎 더 올려"),
            arrayOf("승모 내려"),
            arrayOf("무릎 더 굽혀"),
            arrayOf("팔꿈치 고정"),

        )
    }
    fun calculateFeedBack(exercise: Int, scoreList: ArrayList<Int>):String{
        if (exercise >= 0 && scoreList.size > 0){
            var min_index = scoreList.indexOf(Collections.min(scoreList))
            println("log : " + exercise.toString() + scoreList.toString() + min_index.toString())
            if (scoreList.get(min_index) > 80){
                return "잘했어요"
            }
            return this.feedBackMatrix[exercise][min_index]
        } else{
            return ""
        }

    }
}