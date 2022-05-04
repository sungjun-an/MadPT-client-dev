package com.example.madpt.login

import android.app.Application
import com.example.madpt.R
import com.kakao.sdk.common.KakaoSdk

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
    }

}