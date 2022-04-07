package com.example.madpt.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.madpt.MainActivity
import com.example.madpt.databinding.ActivityStartProfileBinding

class StartProfile : AppCompatActivity() {

    private lateinit var binding: ActivityStartProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.IntegerStature.value=160
        binding.IntegerStature.maxValue=250
        binding.IntegerStature.minValue=0
        binding.DecimalStature.value=0
        binding.DecimalStature.maxValue=9
        binding.DecimalStature.minValue=0

        binding.IntegerWeight.value=50
        binding.IntegerWeight.maxValue=200
        binding.IntegerWeight.minValue=0
        binding.DecimalWeight.value=0
        binding.DecimalWeight.maxValue=99
        binding.DecimalWeight.minValue=0

        binding.checkButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}