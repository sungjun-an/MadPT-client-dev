package com.example.madpt.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.madpt.MainActivity
import com.example.madpt.databinding.ActivityStartProfileBinding
import com.example.madpt.login.LoginActivity
import com.example.madpt.splash.SplashActivity
import com.example.madpt.testmodel

class StartProfile : AppCompatActivity() {

    private lateinit var binding: ActivityStartProfileBinding

    @SuppressWarnings("unchecked")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.IntegerStature.maxValue =250
        binding.IntegerStature.minValue = 0
        binding.DecimalStature.maxValue = 9
        binding.DecimalStature.minValue = 0

        binding.IntegerWeight.maxValue = 200
        binding.IntegerWeight.minValue = 0
        binding.DecimalWeight.maxValue = 99
        binding.DecimalWeight.minValue = 0

        binding.IntegerStature.value = 160
        binding.DecimalStature.value = 0
        binding.IntegerWeight.value = 50
        binding.DecimalWeight.value = 0


        binding.checkButton.setOnClickListener{
            val height: Double = binding.IntegerStature.value.toDouble() + binding.DecimalStature.value/100
            val weight: Double = binding.IntegerWeight.value.toDouble() + binding.DecimalWeight.value/100
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("weight", weight)
            intent.putExtra("height", height)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}