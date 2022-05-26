package com.example.madpt.API.food

import com.example.madpt.API.diet.daily_diet
import com.example.madpt.API.social.Friends
import com.example.madpt.API.social.SocialRank
import com.example.madpt.API.statistic.DailyDietList
import com.example.madpt.API.statistic.MonthDataDateBy
import com.example.madpt.API.statistic.SummaryData
import com.example.madpt.API.statistic.TrainRecordList
import com.example.madpt.API.routine.GetTrainRoutine
import com.example.madpt.API.statistic.*
import com.example.madpt.API.trainresult.Train_result

interface GetFoodList{
    fun getFoodList(testing: ArrayList<food_list>)
}

interface GetTrainRecordList{
    fun getTrainRecord(trainRecord: ArrayList<TrainRecordList>)
}

interface GetMonthDataList{
    fun getMonthDataList(monthDataList: ArrayList<MonthDataDateBy>)
}

interface GetSummaryData{
    fun getSummaryDataList(summaryData: SummaryData)
}

interface GetDailyDietList{
    fun getDailyDiet(dailyDiet: DailyDietStatistic)
}

interface GetSocialRank{
    fun getSocialRank(Rank: ArrayList<Friends>)
}

interface GetExerciseRoutineList{
    fun getExerciseRoutineList(exerciseRoutineList: GetTrainRoutine)
}

interface PostDietList{
    fun postDietList()
}