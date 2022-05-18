package com.example.madpt.training.trainingCamera

import com.example.madpt.testmodel
import com.example.madpt.training.TrainingList
import com.example.madpt.training.trainingCamera.data.TrainingData

interface onTrainingResultClickLisner {
    fun viewSingleChart(chartDataList: ArrayList<TrainingData>, i: Int)
    fun viewSingleReview(chartDataList: ArrayList<TrainingData>, i: Int)
}