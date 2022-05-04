package com.example.madpt.API.food

import com.example.madpt.API.diet.daily_diet
import com.example.madpt.API.statistic.MonthDataDateBy
import com.example.madpt.API.statistic.SummaryData
import com.example.madpt.API.statistic.TrainRecordList
import com.example.madpt.API.trainresult.Train_result

interface GetFoodList{
    fun getFoodList(testing: ArrayList<food_list>)
}

interface PostDietList{
    fun postDietList(dietList: daily_diet)
}

interface PostTrainResultList{
    fun postTrainResult(trainList: Train_result)
}

interface GetTrainRecordList{
    fun getTrainRecord(trainRecord: ArrayList<TrainRecordList>)
}

interface GetMonthDataList{
    fun getMonthDataList(monthData: ArrayList<MonthDataDateBy>)
}

interface GetSummaryData{
    fun getSummaryDataList(summaryData: SummaryData)
}