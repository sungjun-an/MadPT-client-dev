package com.example.madpt.training.trainingCamera.algorithm

import android.graphics.PointF
import androidx.core.graphics.minus
import com.example.madpt.testmodel
import com.example.madpt.training.trainingCamera.data.KeyPoint
import com.example.madpt.training.trainingCamera.data.Person
import kotlin.math.*

class MadPT {
    var state: Int
    var side: String
    var count: MutableList<Int>
    var max_score: MutableList<Double>
    var init_size: MutableList<Int>
    var dataList: ArrayList<Int> = ArrayList()
    var feedbacks: List<Int>

    init {
        this.state = 0
        this.side = ""
        this.count = mutableListOf(0, 0, 0, 0) // 0: pushup, 1: squat, 2: lunge, 3: shoulder_press
        this.max_score = mutableListOf(0.0, 0.0, 0.0, 0.0) // 0: pushup, 1: squat, 2: lunge, 3: shoulder_press
        this.init_size = mutableListOf(210, 210) // 0: right_leg, 1: left_leg
        this.feedbacks = listOf(0, 1, 2, 3) // 0: bad, 1: good, 2: great, 3: excellent
    }

    fun calculate_angle(p1: PointF, p2: PointF, p3: PointF): Double {
        var vector1 : PointF = p1 - p2
        var vector2 : PointF = p3 - p2

        var value1 = (vector1.x * vector2.x + vector1.y * vector2.y)
        var value2 = sqrt(vector1.x.pow(2) + vector1.y.pow(2))
        var value3 = sqrt(vector2.x.pow(2) + vector2.y.pow(2)+ 0.00001)

        var theta : Double = acos(value1 / (value2 * value3) + 0.00001)

        return theta * (180 / PI)
    }

    fun calculate_length(p1: PointF, p2: PointF): Float {
        return sqrt((p1.x - p2.x).pow(2) + (p1.y - p2.y).pow(2))
    }

    fun excrcise_finder(excrcise: testmodel, person: List<Person>): ArrayList<Int>{
        if (excrcise.titles == "PUSH UP"){
            dataList = push_up(person)
        }
        else if (excrcise.titles == "SQUAT"){
            dataList = squat(person)
        }
        else if (excrcise.titles == "LUNGE"){
            dataList = lunge(person)
        }
        else if (excrcise.titles == "SHOULDER PRESS") {
            dataList = shoulder_press(person)
        }
        return dataList
    }

    fun push_up(person: List<Person>): ArrayList<Int> {
        val body_parts: List<KeyPoint> = person[0].keyPoints
        var currentFeedback = -1
        side = if ((body_parts[5].score + body_parts[7].score + body_parts[9].score) >
                   (body_parts[6].score + body_parts[8].score + body_parts[10].score)) "left"
               else "right"

        val observe_point: List<Int> = if (side == "left") listOf(5, 7, 9) else listOf(6, 8, 10)
        val arm_angle : Double = calculate_angle(
            body_parts[observe_point[0]].coordinate,
            body_parts[observe_point[1]].coordinate,
            body_parts[observe_point[2]].coordinate
        )

        if (body_parts[observe_point[0]].score +
            body_parts[observe_point[1]].score +
            body_parts[observe_point[2]].score > 1
        ){

            if(arm_angle < 110 && state == 0){
                state = 1
                println("down")
            }

            if (arm_angle > 160 && state == 1){
                state = 0
                println("up")
                count[0] = count[0] + 1
                println(count[0])
                if(max_score[0] < 10){
                    println("bad")
                    currentFeedback = 0
                }
                else if(max_score[0] < 20){
                    println("good")
                    currentFeedback = 1
                }
                else if(max_score[0] < 30){
                    println("great")
                    currentFeedback = 2
                }
                else{
                    println("excellent!!")
                    currentFeedback = 3
                }
                max_score[0] = 0.0
            }

            if(arm_angle < 110){
                if(max_score[0] < arm_angle - 80){
                    max_score[0] = arm_angle - 80
                }
            }
        }

        dataList.add(0, count[0])
        dataList.add(1, (max_score[0].toInt()))
        dataList.add(2, currentFeedback)

        return dataList
    }

    fun squat(person: List<Person>): ArrayList<Int> {
        var body_parts: List<KeyPoint> = person[0].keyPoints
        var currentFeedback = -1
        side = if ((body_parts[11].score + body_parts[13].score + body_parts[15].score) >
            (body_parts[12].score + body_parts[14].score + body_parts[16].score)) "left"
        else "right"

        var observe_point: List<Int> = if (side == "left") listOf(11, 13, 15) else listOf(12, 14, 16)
        var arm_angle : Double = calculate_angle(
            body_parts[observe_point[0]].coordinate,
            body_parts[observe_point[1]].coordinate,
            body_parts[observe_point[2]].coordinate
        )

        if (body_parts[observe_point[0]].score +
            body_parts[observe_point[1]].score +
            body_parts[observe_point[2]].score > 1
        ){
            if(arm_angle < 110 && state == 0){
                state = 1
                println("down")
            }

            if (arm_angle > 160 && state == 1){
                state = 0
                println("up")
                count[1] = count[1] + 1

                if(max_score[1] < 10){
                    println("bad")
                    currentFeedback = 0
                }
                else if(max_score[1] < 20){
                    println("good")
                    currentFeedback = 1
                }
                else if(max_score[1] < 30){
                    println("great")
                    currentFeedback = 2
                }
                else{
                    println("excellent!!")
                    currentFeedback = 3
                }
                max_score[1] = 0.0
            }

            if(arm_angle < 110){
                if(max_score[1] < arm_angle - 80){
                    max_score[1] = arm_angle - 80
                }
            }
        }

        dataList.add(0, count[1])
        dataList.add(1, (max_score[1].toInt()))
        dataList.add(2, currentFeedback)

        return dataList
    }

    fun lunge(person: List<Person>): ArrayList<Int>{
        val body_parts: List<KeyPoint> = person[0].keyPoints
        var currentFeedback = -1

        val right_leg: Float = calculate_length(
            body_parts[12].coordinate,
            body_parts[14].coordinate
        )
        val left_leg: Float = calculate_length(
            body_parts[11].coordinate,
            body_parts[13].coordinate
        )

        if(left_leg < init_size[1] * 0.5){
            side = "left"
            if (state == 0){
                println("left down")
                state = 1
            }
        }

        if(right_leg < init_size[0] * 0.5){
            side = "right"
            if (state == 0){
                println("right down")
                state = 1
            }
        }

        if(side == "left" && state == 1){
            if (left_leg > init_size[1] * 0.9){
                state = 0
                println("left up")
                side = ""
            }
        }

        if(side == "right" && state == 1){
            if (right_leg > init_size[0] * 0.9){
                state = 0
                println("right up")
                side = ""
            }
        }

        dataList.add(0, count[2])
        dataList.add(1, max_score[2].toInt())

        return dataList
    }

    fun shoulder_press(person: List<Person>): ArrayList<Int>{
        val body_parts: List<KeyPoint> = person[0].keyPoints
        var currentFeedback = -1

        val left_arm_angle : Double = calculate_angle(
            body_parts[5].coordinate,
            body_parts[7].coordinate,
            body_parts[9].coordinate
        )
        val right_arm_angle : Double = calculate_angle(
            body_parts[6].coordinate,
            body_parts[8].coordinate,
            body_parts[10].coordinate
        )

        if (left_arm_angle < 95 && right_arm_angle < 95 && state == 0){
            state = 1
            println("down")
        }

        if (left_arm_angle > 150 && right_arm_angle > 150 && state == 1) {
            state = 0
            println("up")
            count[3] += 1
        }

        dataList.add(0, count[3])
        dataList.add(1, max_score[3].toInt())

        return dataList
    }

}