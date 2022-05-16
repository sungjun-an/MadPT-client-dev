package com.example.madpt.API.routine

import android.content.Context
import android.util.Log
import com.example.madpt.API.RetrofitClass
import com.example.madpt.API.food.GetExerciseRoutineList
import com.example.madpt.API.food.Get_Food
import com.example.madpt.loading.LoadingDialog
import com.example.madpt.splash.SplashActivity.Companion.userId
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetTrainRoutineCall(listen: GetExerciseRoutineList, context: Context) {
    private val context = context
    private val routine = listen
    private var exerciseRoutineList: GetTrainRoutine? = null

    fun GetTrainRoutine(){
        val dialog = LoadingDialog(context)
        dialog.showDialog()

        RetrofitClass.service.getTrainRoutine(userId).enqueue(object : Callback<GetTrainRoutine> {
            override fun onResponse(call: Call<GetTrainRoutine>, response: Response<GetTrainRoutine>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    exerciseRoutineList = response.body()

                    exerciseRoutineList?.let { routine.getExerciseRoutineList(it) }
                    dialog.loadingDismiss()
                    Log.d("YMC", "onResponse 성공: " + exerciseRoutineList?.toString());
                } else {
                    dialog.loadingDismiss()
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("YMC", "onResponse 실패 ${response}, ${response.body()}")
                }
            }
            override fun onFailure(call: Call<GetTrainRoutine>, t: Throwable) {
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
                dialog.loadingDismiss()
            }
        })
    }
}