package com.example.madpt.API.statistic

import android.content.Context
import android.util.Log
import com.example.madpt.API.RetrofitClass
import com.example.madpt.API.food.GetSummaryData
import com.example.madpt.API.food.Get_Food
import com.example.madpt.loading.LoadingDialog
import com.example.madpt.splash.SplashActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetSummaryDataCall(listen: GetSummaryData, context: Context) {

    private val summaryDataListen = listen
    private var summary: SummaryData? = null
    private val context = context

    fun getSummaryData() {

        val dialog = LoadingDialog(context)
        dialog.showDialog()

        val timestamp = System.currentTimeMillis()
        RetrofitClass.service.getSummaryData(SplashActivity.userId, timestamp).enqueue(object : Callback<SummaryData> {
            override fun onResponse(call: Call<SummaryData>, response: Response<SummaryData>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    summary = response.body()

                    summary?.let { summaryDataListen.getSummaryDataList(it) }
                    dialog.loadingDismiss()
                    Log.d("YMC", "onResponse 성공: " + summary?.toString());
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    dialog.loadingDismiss()
                    Log.d("YMC", "onResponse 실패")
                }
            }
            override fun onFailure(call: Call<SummaryData>, t: Throwable) {
                dialog.loadingDismiss()
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}