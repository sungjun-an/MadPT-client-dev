package com.example.madpt.API.food

import android.content.Context
import android.util.Log
import com.example.madpt.API.RetrofitClass
import com.example.madpt.loading.LoadingDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetFoodListCall(listen: GetFoodList, context: Context) {
    private val FoodList = listen
    private val context = context

    var foodList: Get_Food? = null

    fun Get(name: String) {

        val dialog = LoadingDialog(context)
        dialog.showDialog()

        RetrofitClass.service.getdata(name).enqueue(object : Callback<Get_Food> {
            override fun onResponse(call: Call<Get_Food>, response: Response<Get_Food>) {
                if (response.isSuccessful) {
                    // 정상적으로 통신이 성고된 경우
                    foodList = response.body()

                    foodList?.test?.let { FoodList.getFoodList(it) }
                    dialog.loadingDismiss()
                    Log.d("YMC", "onResponse 성공: " + foodList?.toString());
                } else {
                    dialog.loadingDismiss()
                    // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                    Log.d("YMC", "onResponse 실패")
                }
            }
            override fun onFailure(call: Call<Get_Food>, t: Throwable) {
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
                dialog.loadingDismiss()
            }
        })

    }
}