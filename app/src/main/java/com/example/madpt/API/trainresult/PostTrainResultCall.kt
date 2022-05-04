package com.example.madpt.API.trainresult

import android.util.Log
import com.example.madpt.API.RetrofitClass
import com.example.madpt.splash.SplashActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostTrainResultCall {

    fun PostTrainResult(trainResult: Train_result){

        RetrofitClass.service.postTrainResult(SplashActivity.userId,trainResult).enqueue(object :
            Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    Log.d("YMC", "onResponse 성공: $response");
                } else {
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("YMC", "onResponse 실패")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}