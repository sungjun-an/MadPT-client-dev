package com.example.madpt.splash

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.madpt.MainActivity
import com.example.madpt.diet.DietPageActivity
import com.example.madpt.diet.DietSearchActivity
import com.example.madpt.diet.SearchFoodDataModifySaveActivity
import com.example.madpt.login.LoginActivity
import com.example.madpt.main.MainPageFragment
import com.example.madpt.profile.StartProfile
import com.kakao.sdk.user.UserApiClient
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class SplashActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, StartProfile::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                },1000)
            }
            else if (tokenInfo != null) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                },1000)
            }
        }
    }
}