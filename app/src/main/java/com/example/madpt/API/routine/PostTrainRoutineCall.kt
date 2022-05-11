package com.example.madpt.API.routine

import android.content.Context
import android.util.Log
import com.example.madpt.API.PostResponse
import com.example.madpt.API.RetrofitClass
import com.example.madpt.API.member.MemberInfo
import com.example.madpt.loading.LoadingDialog
import com.example.madpt.splash.SplashActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostTrainRoutineCall(context: Context) {

    private val context = context

    fun postTrainRoutineCall(postTrain: PostTrainRoutine) {

        val dialog = LoadingDialog(context)
        dialog.showDialog()

        RetrofitClass.service.postTrainRoutine(SplashActivity.userId, postTrain).enqueue(object :
            Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if (response.isSuccessful) {
                    dialog.loadingDismiss()
                    Log.d("YMC", "onResponse 성공: ${response.body()}");
                } else {
                    dialog.loadingDismiss()
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("YMC", "PostMember 실패")
                }
            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
            }
        })
    }
}