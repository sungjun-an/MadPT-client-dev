package com.example.madpt.API.statistic

import android.content.Context
import android.util.Log
import com.example.madpt.API.RetrofitClass
import com.example.madpt.API.food.GetMonthDataList
import com.example.madpt.loading.LoadingDialog
import com.example.madpt.splash.SplashActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetMonthDataCall(listen: GetMonthDataList, context: Context) {

    private val monthDataListen = listen
    private var monthData: MonthData? = null
    private val context = context

    fun getMonthData(month: Int){

        val dialog = LoadingDialog(context)
        dialog.showDialog()

        RetrofitClass.service.getCalenderRecord(SplashActivity.userId, month).enqueue(object :
            Callback<MonthData> {
            override fun onResponse(call: Call<MonthData>, response: Response<MonthData>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    monthData = response.body()

                    monthData?.monthData?.let { monthDataListen.getMonthDataList(it) }
                    dialog.loadingDismiss()
                    Log.d("YMC", "onResponse 성공: " + monthData?.toString());
                } else {
                    dialog.loadingDismiss()
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("YMC", "onResponse 실패")
                }
            }
            override fun onFailure(call: Call<MonthData>, t: Throwable) {
                dialog.loadingDismiss()
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}