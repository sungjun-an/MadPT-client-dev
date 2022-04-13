package com.example.madpt.diet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madpt.R
import com.example.madpt.databinding.ActivityDietPageBinding

class DietPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDietPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDietPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}