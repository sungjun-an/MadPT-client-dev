package com.example.madpt.diet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.madpt.databinding.ActivitySearchFoodDataModifySaveBinding
import com.example.madpt.*

class SearchFoodDataModifySaveActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchFoodDataModifySaveBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchFoodDataModifySaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val room = intent.getSerializableExtra("roomInfo") as FoodData
        Log.d("확인", "여기까지3")

        binding.modifyFoodName.setText(room.food_name)
        binding.modifyCar.setText(room.default_kcal.toString())
        binding.modifyFat.setText(room.default_fat.toString())
        binding.modifyProtein.setText(room.default_fat.toString())
        binding.modifyFoodCount.setText("1")
        binding.modifyFoodGram.setText(room.default_weight.toString())
        binding.oneTimeText.text = room.default_weight.toString()

    }
}