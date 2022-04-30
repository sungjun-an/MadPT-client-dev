package com.example.madpt.API.statistic

import android.util.Log
import com.example.madpt.API.RetrofitClass
import com.example.madpt.API.food.GetTrainRecordList
import com.example.madpt.API.food.Get_Food
import com.example.madpt.splash.SplashActivity.Companion.userId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp

class GetTrainRecordCall(listen: GetTrainRecordList) {
    val trainRecordListen = listen
    var trainRecord: TrainRecord? = null
    fun trainRecord(start:Timestamp, end:Timestamp){
        RetrofitClass.service.getTrainRecord(userId,start,end).enqueue(object : Callback<TrainRecord> {
            override fun onResponse(call: Call<TrainRecord>, response: Response<TrainRecord>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    trainRecord = response.body()

                    trainRecord?.record_list?.let { trainRecordListen.getTrainRecord(it) }
                    Log.d("YMC", "onResponse 성공: " + trainRecord?.toString());
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("YMC", "onResponse 실패")
                }
            }
            override fun onFailure(call: Call<TrainRecord>, t: Throwable) {
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}