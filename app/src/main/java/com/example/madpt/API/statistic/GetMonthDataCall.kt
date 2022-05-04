package com.example.madpt.API.statistic

import android.util.Log
import com.example.madpt.API.RetrofitClass
import com.example.madpt.API.food.GetMonthDataList
import com.example.madpt.splash.SplashActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetMonthDataCall(listen: GetMonthDataList) {

    val monthDataListen = listen
    var monthData: MonthData? = null

    fun getMonthData(month: Int){
        RetrofitClass.service.getCalenderRecord(SplashActivity.userId, month).enqueue(object :
            Callback<MonthData> {
            override fun onResponse(call: Call<MonthData>, response: Response<MonthData>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    monthData = response.body()

                    monthData?.monthData?.let { monthDataListen.getMonthDataList(it) }
                    Log.d("YMC", "onResponse 성공: " + monthData?.toString());
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("YMC", "onResponse 실패")
                }
            }
            override fun onFailure(call: Call<MonthData>, t: Throwable) {
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}