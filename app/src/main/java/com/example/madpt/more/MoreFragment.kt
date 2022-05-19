package com.example.madpt.more

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.madpt.R
import com.example.madpt.databinding.FragmentMainPageBinding
import com.example.madpt.databinding.FragmentMoreBinding
import com.example.madpt.splash.SplashActivity
import com.example.madpt.splash.SplashActivity.Companion.userNickName
import com.example.madpt.splash.SplashActivity.Companion.userProfile
import com.kakao.sdk.user.UserApiClient


class MoreFragment : Fragment() {
    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        Glide.with(this).load(userProfile).into(binding.profileImage)
        binding.userName.text = "이름 : " + userNickName

        binding.logoutButton.setOnClickListener() {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                } else {
                    Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                }
            }
        }




        return binding.root
    }
}

