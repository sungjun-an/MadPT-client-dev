package com.example.madpt.API.member

import android.content.Context
import android.util.Log
import com.example.madpt.API.PostResponse
import com.example.madpt.API.RetrofitClass
import com.example.madpt.splash.SplashActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostMemberInfoCall(context: Context) {

    fun PostMember(memberInfo: MemberInfo) {
        RetrofitClass.service.postMember(SplashActivity.userId, memberInfo).enqueue(object :
            Callback<PostResponse> {
            override fun onResponse(call: Call<PostResponse>, response: Response<PostResponse>) {
                if (response.isSuccessful) {
                    Log.d("YMC", "onResponse 성공: ${response.body()}");
                } else {
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