package com.example.madpt.API.trainresult

import android.content.Context
import android.util.Log
import com.example.madpt.API.PostResponse
import com.example.madpt.API.RetrofitClass
import com.example.madpt.loading.LoadingDialog
import com.example.madpt.splash.SplashActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostTrainResultCall(context: Context) {

    private val context = context

    fun PostTrainResult(trainResult: Train_result){

        val dialog = LoadingDialog(context)
        dialog.showDialog()

        RetrofitClass.service.postTrainResult(SplashActivity.userId,trainResult).enqueue(object :
            Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                        dialog.loadingDismiss()
                    Log.d("YMC", "onResponse 성공: $response");
                } else {
                    dialog.loadingDismiss()
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("YMC", "onResponse 실패")
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                dialog.loadingDismiss()
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}