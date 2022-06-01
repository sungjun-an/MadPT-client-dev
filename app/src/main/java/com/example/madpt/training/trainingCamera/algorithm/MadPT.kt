package com.example.madpt.training.trainingCamera.algorithm

import android.graphics.PointF
import androidx.core.graphics.minus
import com.example.madpt.testmodel
import com.example.madpt.training.trainingCamera.data.KeyPoint
import com.example.madpt.training.trainingCamera.data.Person
import com.example.madpt.training.trainingCamera.data.TrainingData
import kotlin.math.*
import java.util.Random


class MadPT {
    var state: Int
    var side: String
    var count: MutableList<Int>
    var max_score: MutableList<Double>
    var init_size: MutableList<Int>
    var dataList: ArrayList<Int> = ArrayList()
    var feedbacks: List<Int>
    var trainingDataList: ArrayList<TrainingData> = ArrayList()
    var random: Random = Random()
    var score: Array<Array<Int>> = arrayOf(
        arrayOf(0,0,100,100),
        arrayOf(0,100,100),
        arrayOf(0, 100, 100),
        arrayOf(100, 100, 0, 0),
        arrayOf(0,0,0),
        arrayOf(0,0),
        arrayOf(0),
        arrayOf(0)
    )
    var tmp: Double
    var frame: Int
    var left_shoulder: Array<Float> = arrayOf(0f)
    var right_shoulder: Array<Float> = arrayOf(0f)
    var left_elbow: Array<Float> = arrayOf(0f)
    var right_elbow: Array<Float> = arrayOf(0f)

    init {
        this.state = 0
        this.side = ""
        this.count = mutableListOf(0, 0, 0, 0, 0, 0, 0, 0) // 0: pushup, 1: squat, 2: lunge, 3: shoulder_press
        this.max_score = mutableListOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0) // 0: pushup, 1: squat, 2: lunge, 3: shoulder_press
        this.init_size = mutableListOf(210, 210) // 0: right_leg, 1: left_leg
        this.feedbacks = listOf(0, 1, 2, 3) // 0: bad, 1: good, 2: great, 3: excellent
        this.tmp = 0.0
        this.frame = 0
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

    fun calculate_variance(vals: Array<Float>): Double{
        var variance: Double = 0.0
        var mean: Double = vals.average()
        var vsum: Double = 0.0
        vals.forEach { i ->
            vsum += (i - mean) * (i - mean)
        }
        variance = vsum / vals.size
        return variance
    }


    fun init_excrcise_count(excrcise: testmodel){
        when(excrcise.titles){
            "PUSH UP" -> {
                count[0] = 0
            }
            "SQUAT" -> {
                count[1] = 0
            }
            "LUNGE" -> {
                count[2] = 0
            }
            "SHOULDER PRESS" -> {
                count[3] = 0
            }
            "MOUNTAIN CLIMBING" -> {
                count[4] = 0
            }
            "SIDE LATERAL RAISE" -> {
                count[5] = 0
            }
            "SIDE LUNGE" -> {
                count[6] = 0
            }
            "DUMBEL CURL" -> {
                count[7] = 0
            }
        }
    }

    fun excrcise_finder(excrcise: testmodel, person: List<Person>): Pair<ArrayList<Int>, ArrayList<Int>>{
        var p: Pair<ArrayList<Int>, ArrayList<Int>> = Pair(ArrayList(0), ArrayList(0))
        when (excrcise.titles) {
            "PUSH UP" -> {
                p = push_up(person)
            }
            "SQUAT" -> {
                p = squat(person)
            }
            "LUNGE" -> {
                p = lunge(person)
            }
            "SHOULDER PRESS" -> {
                p = shoulder_press(person)
            }
            "MOUNTAIN CLIMBING" -> {
                p = mountain_climbing(person)
            }
            "SIDE LATERAL RAISE" -> {
                p = side_lateral_raise(person)
            }
            "SIDE LUNGE" -> {
                p = side_lunge(person)
            }
            "DUMBEL CURL" -> {
                p = side_lunge(person)
            }
        }
        return p
    }

    fun push_up(person: List<Person>): Pair<ArrayList<Int>, ArrayList<Int>> {
        var trainingData = TrainingData()
        val body_parts: List<KeyPoint> = person[0].keyPoints
        var score = person[0].score
        var score_th:Float = 0.5f
        var min_head_angle = 130
        var max_head_angle = 160
        var min_arm_angle = 90
        var max_arm_angle = 120
        var min_hip_angle = 130
        var max_hip_angle = 160
        var min_depth = 0
        var max_depth = 100
        var return_val:ArrayList<Int> = ArrayList()


        var body_angle = this.calculate_angle(
            PointF(body_parts[15].coordinate.x, 0F),
            body_parts[15].coordinate,
            body_parts[5].coordinate
        )   //신체가 누웠나에 대한 정보
        if (score > score_th && body_angle > 45){
            var left_arm_angle = this.calculate_angle(
                body_parts[5].coordinate,
                body_parts[7].coordinate,
                body_parts[9].coordinate,
            )

            var right_arm_angle = this.calculate_angle(
                body_parts[6].coordinate,
                body_parts[8].coordinate,
                body_parts[10].coordinate
            )

            var left_hip_angle = this.calculate_angle(
                body_parts[5].coordinate,
                body_parts[11].coordinate,
                body_parts[13].coordinate
            )

            var right_hip_angle = this.calculate_angle(
                body_parts[6].coordinate,
                body_parts[12].coordinate,
                body_parts[14].coordinate,
            )

            var hip_angle = (left_hip_angle + right_hip_angle) / 2
            var left_head_angle = this.calculate_angle(
                body_parts[3].coordinate,
                body_parts[5].coordinate,
                body_parts[11].coordinate
            )

            var right_head_angle = this.calculate_angle(
                body_parts[4].coordinate,
                body_parts[6].coordinate,
                body_parts[12].coordinate
            )
            var head_angle = (left_head_angle + right_head_angle) / 2

            var depth = body_parts[7].coordinate.y - body_parts[5].coordinate.y
            var arm_angle = (left_arm_angle + right_arm_angle) / 2

            var depth_score = 0
            if (depth < min_depth){
                depth_score = 100
            } else if(depth < max_depth) {
                depth_score = ((max_depth - depth) / (max_depth - min_depth) * 100).toInt()
            } else {
                depth_score = 0
            }

            var arm_score = 0
            if (arm_angle < min_arm_angle){
                arm_score = 100
            } else if (arm_angle < max_arm_angle){
                arm_score = ((max_arm_angle - arm_angle) / (max_arm_angle - min_arm_angle) * 100).toInt()
            } else {
                arm_score = 0
            }


            var head_score = 0
            if (head_angle > max_head_angle){
                head_score = 100
            } else if (head_angle > min_head_angle) {
                head_score = ((max_head_angle - head_angle) / (max_head_angle - min_head_angle) * 100).toInt()
            } else {
                head_score = 0
            }

            var hip_score = 0
            if (hip_angle > max_hip_angle){
                hip_score = 100
            } else if (hip_angle > min_hip_angle){
                hip_score = ((max_hip_angle - hip_angle) / (max_hip_angle - min_hip_angle) * 100).toInt()
            } else {
                hip_score = 0
            }
            println("checkpoint: "+ depth_score)
            println("checkpoint: " + arm_score)
            println("checkpoint : " + hip_score)
            if (this.score[0][0] < depth_score) {
                this.score[0][0] = depth_score
                println("checkpoint2")
            }
            if (this.score[0][1] < arm_score)
                this.score[0][1] = arm_score
            if (this.score[0][2] > hip_score)
                this.score[0][2] = hip_score
            if (this.score[0][3] > head_score)
                this.score[0][3] = head_score

            if (arm_angle < 120 && this.state == 0){
                this.state = 1
            }

            if (arm_angle > 160 && this.state == 1){
                this.state = 0
                this.count[0] += 1
                return_val.add(0)
                this.score[0].forEach{ i ->
                    return_val.add(i)
                }

                trainingData.excrciseName = 0
                trainingData.excrciseCount = count[0]

                trainingData.excrciseScore = ArrayList(return_val.subList(1, return_val.size)).average().toInt()
                trainingData.exerciseScoreList = return_val
                trainingDataList.add(trainingData)
                this.score[0] = arrayOf(0, 0, 100, 100)
            }

        }
        dataList.add(0, count[0])
        dataList.add(1, (max_score[0].toInt()))
        return Pair(dataList, return_val)
    }

    fun squat(person: List<Person>): Pair<ArrayList<Int>, ArrayList<Int>> {
        val trainingData = TrainingData()
        var return_val:ArrayList<Int> = ArrayList()
        val body_parts: List<KeyPoint> = person[0].keyPoints
        var score = person[0].score
        var min_thigh_angle = 70
        var max_thigh_angle = 120
        var min_calf_angle = 30
        var max_calf_angle = 45
        var min_waist_angle = 30
        var max_waist_angle = 50
        var score_th = 0.4

        if (score > score_th){
            var left_thigh_angle = this.calculate_angle(
                body_parts[11].coordinate,
                body_parts[13].coordinate,
                body_parts[15].coordinate,
            )

            var left_calf_angle = this.calculate_angle(
                PointF(body_parts[15].coordinate.x, 0f),
                body_parts[15].coordinate,
                body_parts[13].coordinate
            )

            var left_waist_angle = this.calculate_angle(
                PointF(body_parts[11].coordinate.x, 0f),
                body_parts[11].coordinate,
                body_parts[5].coordinate
            )

            var right_thigh_angle = this.calculate_angle(
                body_parts[12].coordinate,
                body_parts[14].coordinate,
                body_parts[16].coordinate,
            )

            var right_calf_angle = this.calculate_angle(
                PointF(body_parts[16].coordinate.x, 0f),
                body_parts[16].coordinate,
                body_parts[14].coordinate
            )

            var right_waist_angle = this.calculate_angle(
                PointF(body_parts[12].coordinate.x, 0f),
                body_parts[12].coordinate,
                body_parts[6].coordinate
            )

            var thigh_angle = (left_thigh_angle + right_thigh_angle) / 2
            var calf_angle = (left_calf_angle + right_calf_angle) / 2
            var waist_angle = (left_waist_angle + right_waist_angle) / 2

            var thigh_score = 0
            if (thigh_angle < min_thigh_angle){
                thigh_score = 100
            } else if (thigh_angle < max_thigh_angle){
                thigh_score = ((max_thigh_angle - thigh_angle) / (max_thigh_angle - min_thigh_angle) * 100).toInt()
            } else {
                thigh_score = 0
            }

            var calf_score = 0
            if (calf_angle < min_calf_angle){
                calf_score = 100
            } else if (calf_angle < max_calf_angle){
                calf_score = ((max_calf_angle - calf_angle) / (max_calf_angle - min_calf_angle) * 100).toInt()
            } else {
                calf_score = 0
            }

            var waist_score = 0
            if (waist_angle < min_waist_angle){
                waist_score = 100
            } else if (waist_angle < max_waist_angle) {
                waist_score = ((max_waist_angle - waist_angle) / (max_waist_angle - min_waist_angle) * 100).toInt()
            } else {
                waist_score = 0
            }

            println("log123 left waist : " + left_waist_angle)
            println("log123 right waist : " + right_waist_angle)
            println("log123 waist score : " + waist_score)

            if (this.score[1][0] < thigh_score)
                this.score[1][0] = thigh_score

            if (this.score[1][1] > calf_score && this.score[1][1] - calf_score < 30)
                this.score[1][1] = calf_score

            if (this.score[1][2] > waist_score && this.score[1][2] - waist_score < 30)
                this.score[1][2] = waist_score

            if (left_thigh_angle < max_thigh_angle && this.state == 0){
                this.state = 1
            }


            if (left_thigh_angle > 160 && right_thigh_angle > 160 && this.state == 1){
                this.state = 0
                this.count[1] += 1
                return_val.add(1)
                this.score[1].forEach{i ->
                    return_val.add(i)
                }
                trainingData.excrciseName = 1
                trainingData.excrciseCount = count[1]

                trainingData.excrciseScore = ArrayList(return_val.subList(1, return_val.size)).average().toInt()
                trainingData.exerciseScoreList = return_val
                trainingDataList.add(trainingData)
                this.score[1] = arrayOf(0,100,100)
                print("log123 : " + return_val)
            }
        }


        dataList.add(0, count[1])
        dataList.add(1, (max_score[1].toInt()))

        return Pair(dataList, return_val)
    }

    fun lunge(person: List<Person>): Pair<ArrayList<Int>, ArrayList<Int>>{
        var trainingData = TrainingData()
        val body_parts: List<KeyPoint> = person[0].keyPoints
        var score = person[0].score
        var score_th = 0.5f
        var return_val:ArrayList<Int> = ArrayList()
        var min_down_rate = 0.3
        var max_down_rate = 0.8
        var min_angle = 5
        var max_angle = 20
        var right_angle:Double = 0.0
        var left_angle:Double = 0.0
        var down_rate:Double = 0.0

        if (score > score_th){
            var left_knee_point = body_parts[13].coordinate.y
            var right_knee_point = body_parts[14].coordinate.y
            var left_ankle_point = body_parts[15].coordinate.y
            var right_ankle_point = body_parts[16].coordinate.y

            var left_down_rate = (right_knee_point - left_knee_point) / (left_ankle_point - left_knee_point + 1e-5)
            var right_down_rate = (left_knee_point - right_knee_point) / (right_ankle_point - right_knee_point + 1e-5)

            if (left_down_rate > min_down_rate && this.state == 0){
                this.state = 1
                this.side = "left"
            }


            if (right_down_rate > min_down_rate && this.state == 0){
                this.state = 1
                this.side = "right"
            }


            if (this.side == "left")
                down_rate = left_down_rate

            if (this.side == "right")
                down_rate = right_down_rate

            if (this.side == "left"){
                left_angle = this.calculate_angle(
                    PointF(body_parts[15].coordinate.x, 0f),
                    body_parts[15].coordinate,
                    body_parts[13].coordinate
                )
                right_angle = this.calculate_angle(
                    PointF(body_parts[14].coordinate.x, 0f),
                    body_parts[14].coordinate,
                    body_parts[12].coordinate
                )
            }


            if (this.side == "right"){
                left_angle = this.calculate_angle(
                    PointF(body_parts[13].coordinate.x, 0f),
                    body_parts[13].coordinate,
                    body_parts[11].coordinate
                )
                right_angle = this.calculate_angle(
                    PointF(body_parts[16].coordinate.x, 0f),
                    body_parts[16].coordinate,
                    body_parts[14].coordinate
                )
            }

            var down_score = 0
            if (down_rate > max_down_rate){
                down_score = 100
            } else if (down_rate > min_down_rate){
                down_score = ((max_down_rate - down_rate) / (max_down_rate - min_down_rate) * 100).toInt()
            } else {
                down_score = 0
            }

            var right_score = 0
            if (right_angle < min_angle){
                right_score = 100
            } else if (right_angle < max_angle){
                right_score = ((max_angle - right_angle) / (max_angle - min_angle) * 100).toInt()
            } else{
                right_score = 0
            }

            var left_score = 0
            if (left_angle < min_angle){
                left_score = 100
            } else if (left_angle < max_angle){
                left_score = ((max_angle - left_angle) / (max_angle - min_angle) * 100).toInt()
            } else {
                left_score = 0
            }

            if (this.score[2][0] < down_score)
                this.score[2][0] = down_score

            if (this.score[2][1] > right_score)
                this.score[2][1] = right_score

            if (this.score[2][2] > left_score)
                this.score[2][2] = left_score

            if (down_rate < 0.2 && this.state == 1){
                this.state = 0
                this.count[2] += 1
                return_val.add(2)
                this.score[2].forEach{ i ->
                    return_val.add(i)
                }
                this.score[2] = arrayOf(0, 100, 100)
                this.side = ""
                trainingData.excrciseName = 2
                trainingData.excrciseCount = count[2]

                trainingData.excrciseScore = ArrayList(return_val.subList(1, return_val.size)).average().toInt()
                trainingData.exerciseScoreList = return_val
                trainingDataList.add(trainingData)
            }
        }

        dataList.add(0, count[2])
        dataList.add(1, (max_score[2].toInt()))

        return Pair(dataList, return_val)


    }

    fun shoulder_press(person: List<Person>): Pair<ArrayList<Int>, ArrayList<Int>>{
        var trainingData = TrainingData()
        val body_parts: List<KeyPoint> = person[0].keyPoints
        var return_val:ArrayList<Int> = ArrayList()
        var score = person[0].score
        var score_th = 0.5f
        var min_angle = 75
        var max_angle = 100
        var min_vertical_angle = 20
        var max_vertical_angle = 40

        if (score > score_th){
            var left_arm_angle = this.calculate_angle(
                PointF(body_parts[7].coordinate.x, 0f),
                body_parts[7].coordinate,
                body_parts[9].coordinate
            )
            var right_arm_angle = this.calculate_angle(
                PointF(body_parts[6].coordinate.x, 0f),
                body_parts[8].coordinate,
                body_parts[10].coordinate
            )

            var left_elbow_angle = this.calculate_angle(
                body_parts[5].coordinate,
                body_parts[7].coordinate,
                body_parts[9].coordinate
            )

            var right_elbow_angle = this.calculate_angle(
                body_parts[6].coordinate,
                body_parts[8].coordinate,
                body_parts[10].coordinate
            )

            var left_arm_score = 0
            if (left_arm_angle < min_vertical_angle)
                left_arm_score = 100
            else if (left_arm_angle < max_vertical_angle)
                left_arm_score = ((max_vertical_angle - left_arm_angle) / (max_vertical_angle - min_vertical_angle) * 100).toInt()
            else
                left_arm_score = 0

            var right_arm_score = 0
            if (right_arm_angle < min_vertical_angle)
                right_arm_score = 100
            else if (right_arm_angle < max_vertical_angle)
                right_arm_score = ((max_vertical_angle - right_arm_angle) / (max_vertical_angle - min_vertical_angle) * 100).toInt()
            else
                right_arm_score = 0

            var left_elbow_score = 0
            if (left_elbow_angle < min_angle)
                left_elbow_score = 0
            else if (left_elbow_angle < max_angle)
                left_elbow_score = ((max_angle - left_elbow_angle) / (max_angle - min_angle) * 100).toInt()
            else
                left_elbow_score = 0

            var right_elbow_score = 0
            if (right_elbow_angle < min_angle)
                right_elbow_score = 100
            else if (right_elbow_angle < max_angle)
                right_elbow_score = ((max_angle - right_elbow_angle) / (max_angle - min_angle) * 100).toInt()
            else
                right_elbow_score = 0

            if (this.score[3][0] > left_arm_score)
                this.score[3][0] = left_arm_score

            if (this.score[3][1] > right_arm_score)
                this.score[3][1] = right_arm_score

            if (this.score[3][2] < left_elbow_score)
                this.score[3][2] = left_elbow_score

            if (this.score[3][3] < right_elbow_score)
                this.score[3][3] = right_elbow_score

            if (left_elbow_angle > 140 && right_elbow_angle > 140 && this.state == 0)
                this.state = 1

            if (left_elbow_angle < 90 && right_elbow_angle < 90 && this.state == 1){
                this.state = 0
                this.count[3] += 1
                return_val.add(3)
                this.score[3].forEach{ i ->
                    return_val.add(i)
                }

                this.score[3] = arrayOf(100, 100, 0, 0)
                trainingData.excrciseName = 3
                trainingData.excrciseCount = count[3]

                trainingData.excrciseScore = ArrayList(return_val.subList(1, return_val.size)).average().toInt()
                trainingData.exerciseScoreList = return_val
                trainingDataList.add(trainingData)
            }
        }
        dataList.add(0, count[3])
        dataList.add(1, (max_score[3].toInt()))

        return Pair(dataList, return_val)
    }

    fun mountain_climbing(person: List<Person>): Pair<ArrayList<Int>, ArrayList<Int>>{
        var trainingData = TrainingData()
        val body_parts: List<KeyPoint> = person[0].keyPoints
        var return_val:ArrayList<Int> = ArrayList()
        var score = person[0].score
        var score_th = 0.5f
        var min_distance = 100
        var max_distance = 300
        var good_hip_angle1 = 165
        var good_hip_angle2 = 150
        var min_hip_angle = 130

        var body_angle = this.calculate_angle(
            PointF(body_parts[15].coordinate.x, 0f),
            body_parts[15].coordinate,
            body_parts[5].coordinate
        )
        if (score > score_th && body_angle > 45){
            this.frame += 1
            var left_hip_angle = this.calculate_angle(
                body_parts[5].coordinate,
                body_parts[11].coordinate,
                body_parts[15].coordinate
            )

            var right_hip_angle = this.calculate_angle(
                body_parts[6].coordinate,
                body_parts[12].coordinate,
                body_parts[16].coordinate
            )

            var distance_1 = this.calculate_length(
                body_parts[14].coordinate,
                body_parts[8].coordinate,
            )

            var distance_2 = this.calculate_length(
                body_parts[13].coordinate,
                body_parts[7].coordinate,
            )

            var distance = min(distance_1, distance_2)
            var hip_angle = max(right_hip_angle, left_hip_angle)
            tmp += hip_angle

            var knee_score = 0
            if (distance < min_distance){
                knee_score = 100
            } else if (distance < max_distance){
                knee_score = ((max_distance - distance) / (max_distance - min_distance) * 100).toInt()
            } else {
                knee_score = 0
            }


            if (this.score[4][2] < knee_score)
                this.score[4][2] = knee_score

            if (knee_score > 1 && this.state == 0)
                this.state = 1

            if (knee_score == 0 && this.state == 1){
                hip_angle = this.tmp / this.frame
                var hip_score1 = 0
                var hip_score2 = 0
                if (hip_angle > good_hip_angle1) {
                    hip_score1 = ((180 - hip_angle) * (100 / (180 - good_hip_angle1))).toInt()
                    hip_score2 = 100
                } else if (hip_angle < good_hip_angle1 && hip_angle > good_hip_angle2) {
                    hip_score1 = 100
                    hip_score2 = 100
                } else {
                    hip_score1 = 100
                    hip_score2 = (((hip_angle - min_hip_angle) / (good_hip_angle2 - min_hip_angle)) * 100).toInt()
                }
                this.score[4][0] = hip_score1
                this.score[4][1] = hip_score2
                this.frame = 0
                this.tmp = 0.0
                this.state = 0
                this.count[4] += 1
                return_val.add(4)
                this.score[4].forEach{ i ->
                    return_val.add(i)
                }
                this.score[4] = arrayOf(100, 100, 0, 0)
                trainingData.excrciseName = 4
                trainingData.excrciseCount = count[4]
                trainingData.excrciseScore = ArrayList(return_val.subList(1, return_val.size)).average().toInt()
                trainingData.exerciseScoreList = return_val
                trainingDataList.add(trainingData)
            }
        }
        dataList.add(0, count[4])
        dataList.add(1, (max_score[4].toInt()))

        return Pair(dataList, return_val)
    }

    fun side_lateral_raise(person: List<Person>): Pair<ArrayList<Int>, ArrayList<Int>> {
        val trainingData = TrainingData()
        var return_val: ArrayList<Int> = ArrayList()
        val body_parts: List<KeyPoint> = person[0].keyPoints
        var score = person[0].score
        var score_th = 0.5
        var min_arm_angle = 30
        var max_arm_angle = 70
        var min_variance = 100
        var max_variance = 400

        if (score > score_th){
            this.frame += 1
            this.left_shoulder.plus(body_parts[5].coordinate.y)
            this.right_shoulder.plus(body_parts[6].coordinate.y)

            var left_arm_angle = this.calculate_angle(
                body_parts[9].coordinate,
                body_parts[5].coordinate,
                body_parts[11].coordinate
            )
            var right_arm_angle = this.calculate_angle(
                body_parts[10].coordinate,
                body_parts[6].coordinate,
                body_parts[12].coordinate
            )

            var arm_angle = (right_arm_angle + left_arm_angle) / 2

            if (arm_angle > max_arm_angle && this.state == 0){
                this.state = 1
            }

            if (arm_angle < min_arm_angle && this.state == 1){
                this.state = 0
                this.frame = 0
                this.count[5] += 1
                var left_variance = this.calculate_variance(this.left_shoulder)
                var right_variance = this.calculate_variance(this.right_shoulder)
                var variance = (left_variance + right_variance) / 2

                return_val.add(5)
                if (variance < min_variance)
                    return_val.add(100)
                else if (variance < max_variance)
                    return_val.add((((max_variance - variance) / (max_variance - min_variance)) * 100).toInt())
                else
                    return_val.add(0)

                trainingData.excrciseName = 5
                trainingData.excrciseCount = count[5]

                trainingData.excrciseScore = ArrayList(return_val.subList(1, return_val.size)).average().toInt()
                trainingData.exerciseScoreList = return_val
                trainingDataList.add(trainingData)
            }
        }
        dataList.add(0, count[5])
        dataList.add(1, (max_score[5].toInt()))

        return Pair(dataList, return_val)
    }

    fun side_lunge(person: List<Person>): Pair<ArrayList<Int>, ArrayList<Int>>{
        val trainingData = TrainingData()
        var return_val:ArrayList<Int> = ArrayList()
        val body_parts: List<KeyPoint> = person[0].keyPoints
        var score = person[0].score
        var score_th = 0.5
        var max_leg_angle = 150
        var min_leg_angle = 100

        if (score > score_th){
            var left_thigh_angle = this.calculate_angle(
                body_parts[11].coordinate,
                body_parts[13].coordinate,
                body_parts[15].coordinate,
            )

            var right_thigh_angle = this.calculate_angle(
                body_parts[12].coordinate,
                body_parts[14].coordinate,
                body_parts[16].coordinate,
            )
            var leg_angle = min(left_thigh_angle, right_thigh_angle)

            var leg_score = 0
            if (leg_angle < min_leg_angle)
                leg_score = 100
            else if (leg_angle < max_leg_angle)
                leg_score = ((max_leg_angle - leg_angle) / (max_leg_angle - min_leg_angle) * 100).toInt()
            else
                leg_score = 0

            if (this.score[6][0] < leg_score)
                this.score[6][0] = leg_score

            if (leg_angle < max_leg_angle && this.state == 0)
                this.state = 1
            if (leg_angle > max_leg_angle && this.state == 1) {
                this.state = 0
                this.count[6] += 1
                return_val.add(6)
                this.score[6].forEach { i ->
                    return_val.add(i)
                }
                this.score[6] = arrayOf(0)
                trainingData.excrciseName = 6
                trainingData.excrciseCount = count[6]

                trainingData.excrciseScore = ArrayList(return_val.subList(1, return_val.size)).average().toInt()
                trainingData.exerciseScoreList = return_val
                trainingDataList.add(trainingData)
            }
        }
        dataList.add(0, count[6])
        dataList.add(1, (max_score[6].toInt()))

        return Pair(dataList, return_val)
    }

    fun dumbel_curl(person: List<Person>): Pair<ArrayList<Int>, ArrayList<Int>>{
        val trainingData = TrainingData()
        var return_val:ArrayList<Int> = ArrayList()
        val body_parts: List<KeyPoint> = person[0].keyPoints
        var score = person[0].score
        var score_th = 0.5
        var max_arm_angle = 90
        var min_arm_angle = 30
        var max_variance = 400
        var min_variance = 100

        if (score > score_th){
            this.frame += 1
            this.left_elbow.plus(body_parts[7].coordinate.y)
            this.right_elbow.plus(body_parts[8].coordinate.y)

            var left_arm_angle = this.calculate_angle(
                body_parts[5].coordinate,
                body_parts[7].coordinate,
                body_parts[9].coordinate
            )
            var right_arm_angle = this.calculate_angle(
                body_parts[6].coordinate,
                body_parts[8].coordinate,
                body_parts[10].coordinate
            )

            var arm_angle = min(right_arm_angle, left_arm_angle)

            if (arm_angle < min_arm_angle && this.state == 0)
                this.state = 1
            if (arm_angle > max_arm_angle && this.state == 1){
                this.state = 0
                this.frame = 0
                this.count[7] += 1
                var left_variance = this.calculate_variance(this.left_elbow)
                var right_variance = this.calculate_variance(this.right_elbow)
                var variance = (left_variance + right_variance) / 2

                return_val.add(7)
                if (variance < min_variance)
                    return_val.add(100)
                else if (variance < max_variance)
                    return_val.add((((max_variance - variance) / (max_variance - min_variance)) * 100).toInt())
                else
                    return_val.add(0)
                this.score[7] = arrayOf(0)
                trainingData.excrciseName = 7
                trainingData.excrciseCount = count[7]
                trainingData.excrciseScore = ArrayList(return_val.subList(1, return_val.size)).average().toInt()
                trainingData.exerciseScoreList = return_val
                trainingDataList.add(trainingData)
            }
        }
        dataList.add(0, count[7])
        dataList.add(1, (max_score[7].toInt()))

        return Pair(dataList, return_val)
    }
}

