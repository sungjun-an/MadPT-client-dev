package com.example.madpt.API.statistic

import android.content.Context
import android.util.Log
import com.example.madpt.API.RetrofitClass
import com.example.madpt.API.food.GetMonthDataList
import com.example.madpt.loading.LoadingDialog
import com.example.madpt.splash.SplashActivity.Companion.userId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class GetMonthDataCall(listen: GetMonthDataList, context: Context) {

    private val monthDataListen = listen
    private lateinit var monthData: MonthData
    private val context = context

    fun getMonthData(month:Long){

        var monthDataList = ArrayList<MonthDataDateBy>()
        monthDataList.add(MonthDataDateBy(0,0.0,0.0))
        monthData = MonthData(monthDataList)
        val dialog = LoadingDialog(context)
        dialog.showDialog()
        RetrofitClass.service.getCalenderRecord(userId, month).enqueue(object :
            Callback<MonthData> {
            override fun onResponse(call: Call<MonthData>, response: Response<MonthData>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    monthData = response.body()!!
                    monthData.monthData.let { monthDataListen.getMonthDataList(it) }
                    dialog.loadingDismiss()
                    Log.d("YMC", "onResponse 성공: $monthData");
                } else {
                    dialog.loadingDismiss()
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    monthData.monthData.let { monthDataListen.getMonthDataList(it) }
                    Log.d("YMC", "onResponse 실패, $response, ${response.body()}")
                }
            }
            override fun onFailure(call: Call<MonthData>, t: Throwable) {
                dialog.loadingDismiss()
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}