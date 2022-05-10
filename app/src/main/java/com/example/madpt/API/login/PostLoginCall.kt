package com.example.madpt.API.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.madpt.API.RetrofitClass
import com.example.madpt.API.member.MemberInfo
import com.example.madpt.API.member.PostMemberInfoCall
import com.example.madpt.splash.SplashActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostLoginCall() {
    fun Postlogin(context: Context, memberInfo: MemberInfo) {
        RetrofitClass.service.postLogin(SplashActivity.userId).enqueue(object :
            Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    if (response.body() == "true") {
                        Toast.makeText(context, "로그인 되었습니다.", Toast.LENGTH_LONG).show()
                        Log.d("YMC", "PostLogin 성공: ${response.body()}")
                    } else{
//                        Log.d("YMC", "PostLogin 성공: ${response.body()}")
                        PostMemberInfoCall(context).PostMember(memberInfo)
                    }
                } else {
                    Log.d("YMC", "Postlogin 실패: ${response}, ${response.errorBody()}, ${response.body()}")
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("YMC", "onFailure 에러: " + t.message.toString())
            }
        })
    }
}