package com.example.madpt.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.madpt.API.login.PostLoginCall
import com.example.madpt.API.member.MemberInfo
import com.example.madpt.MainActivity
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.example.madpt.databinding.ActivityLoginBinding
import com.example.madpt.splash.SplashActivity

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val weight = intent.getDoubleExtra("weight",0.0)
        val height = intent.getDoubleExtra("height", 0.0)
        var memberInfo: MemberInfo? = null

        binding.kakaoLoginBtn.setOnClickListener{
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.d(TAG, "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    } else if (token != null) {
                        Log.d(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                                UserApiClient.instance.me { user, error ->
                                    if (error != null) {
                                        Log.e(TAG, "사용자 정보 요청 실패", error)
                                    } else if (user != null) {
                                        Log.d(
                                            "YMC", "사용자 정보 요청 성공" +
                                                    "\n회원번호: ${user.id}" +
                                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                                    "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}" +
                                                    "\n성별: ${user.kakaoAccount?.gender}"
                                        )
                                        SplashActivity.userId = user.id!!

                                        memberInfo = MemberInfo(
                                            user.kakaoAccount?.profile?.nickname!!,
                                            weight,
                                            height,
                                            user.kakaoAccount?.gender.toString()
                                        )
                                        //TRUE/FALSE 확인 FALSE 일때만 정보 저장 POST 때리기
                                        PostLoginCall().Postlogin(this, memberInfo!!)
                                        startMainActivity()
                                    }
                                }
                        }
                }
            }
        }
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}