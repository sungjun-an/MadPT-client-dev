package com.example.madpt.splash


import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.madpt.MainActivity
import com.example.madpt.login.LoginActivity
import com.example.madpt.profile.StartProfile
import com.kakao.sdk.user.UserApiClient
import java.net.URL
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Base64.getEncoder

class SplashActivity :AppCompatActivity() {
    companion object {
        var userId: Long = 0
        var userProfile : String = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e("YMC", "사용자 정보 요청 실패", error)
                    } else if (user != null) {
                        Log.i(
                            "YMC", "사용자 정보 요청 성공" +
                                    "\n회원번호: ${user.id}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                    "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}" +
                                    "\n성별: ${user.kakaoAccount?.gender}"
                        )
                        userId = user.id!!
                        userProfile = user.kakaoAccount?.profile?.thumbnailImageUrl!!
                    }
                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent = Intent(this, StartProfile::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }, 1500)
                }
                    } else if (tokenInfo != null) {
                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent = Intent(this, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        }, 1500)
                    }
                }
            }



        }

