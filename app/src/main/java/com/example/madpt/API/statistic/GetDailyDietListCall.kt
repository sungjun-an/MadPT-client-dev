package com.example.madpt.API.statistic

import android.content.Context
import android.util.Log
import com.example.madpt.API.RetrofitClass
import com.example.madpt.API.food.GetDailyDietList
import com.example.madpt.loading.LoadingDialog
import com.example.madpt.splash.SplashActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetDailyDietListCall(listen: GetDailyDietList,context: Context) {

    private val context = context
    private val listen = listen
    private var dailyDietList: DailyDietList? = null
    fun getDailyDietListCall(timestamp: Long){
        val dialog = LoadingDialog(context)
        dialog.showDialog()

        RetrofitClass.service.getDailyDietList(SplashActivity.userId, timestamp).enqueue(object :
            Callback<DailyDietList> {
            override fun onResponse(call: Call<DailyDietList>, response: Response<DailyDietList>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    dailyDietList = response.body()

                    dailyDietList?.let { listen.getDailyDiet(it) }
                    dialog.loadingDismiss()
                    Log.d("YMC", "onResponse 성공: " + dailyDietList?.toString());
                } else {
                    dialog.loadingDismiss()
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("YMC", "onResponse 실패")
                }
            }
            override fun onFailure(call: Call<DailyDietList>, t: Throwable) {
                dialog.loadingDismiss()
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
            }
        })

    }
}