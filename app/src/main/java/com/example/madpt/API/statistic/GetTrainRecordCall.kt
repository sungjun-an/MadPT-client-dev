package com.example.madpt.API.statistic

import android.content.Context
import android.util.Log
import com.example.madpt.API.RetrofitClass
import com.example.madpt.API.food.GetTrainRecordList
import com.example.madpt.API.food.Get_Food
import com.example.madpt.loading.LoadingDialog
import com.example.madpt.splash.SplashActivity.Companion.userId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Timestamp

class GetTrainRecordCall(listen: GetTrainRecordList, context: Context) {

    private val trainRecordListen = listen
    private var trainRecord: TrainRecord? = null
    private val context = context

    fun trainRecord(date: Long){

        val dialog = LoadingDialog(context)
        dialog.showDialog()

        RetrofitClass.service.getTrainRecord(userId, date).enqueue(object : Callback<TrainRecord> {
            override fun onResponse(call: Call<TrainRecord>, response: Response<TrainRecord>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    trainRecord = response.body()

                    trainRecord?.record_list?.let { trainRecordListen.getTrainRecord(it) }
                    dialog.loadingDismiss()
                    Log.d("YMC", "onResponse 성공: " + trainRecord?.toString());
                } else {
                    dialog.loadingDismiss()
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("YMC", "onResponse 실패")
                }
            }
            override fun onFailure(call: Call<TrainRecord>, t: Throwable) {
                dialog.loadingDismiss()
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}