package com.example.madpt.API.member

import android.content.Context
import android.util.Log
import com.example.madpt.API.RetrofitClass
import com.example.madpt.API.food.GetExerciseRoutineList
import com.example.madpt.API.food.GetMemberProfile
import com.example.madpt.API.food.Get_Food
import com.example.madpt.loading.LoadingDialog
import com.example.madpt.splash.SplashActivity.Companion.userId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetMemberInfoCall(listen: GetMemberProfile, context: Context) {
    private val context = context
    private var memberInfo : MemberProfile? = null
    private var memberProfile = listen

    fun GetMemberInfo(){
        val dialog = LoadingDialog(context)
        dialog.showDialog()

        RetrofitClass.service.getMemberInfo(userId).enqueue(object : Callback<MemberProfile> {
            override fun onResponse(call: Call<MemberProfile>, response: Response<MemberProfile>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    memberInfo = response.body()

                    memberInfo?.let { memberProfile.getMemberProfile(it) }
                    dialog.loadingDismiss()
                    Log.d("YMC", "onResponse 성공: " );
                } else {
                    dialog.loadingDismiss()
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("YMC", "onResponse 실패 ${response}, ${response.body()}")
                }
            }
            override fun onFailure(call: Call<MemberProfile>, t: Throwable) {
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
                dialog.loadingDismiss()
            }
        })
    }
}