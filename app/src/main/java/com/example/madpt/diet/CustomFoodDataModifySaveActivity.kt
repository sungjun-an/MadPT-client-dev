package com.example.madpt.diet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madpt.API.diet.AddFoodList
import com.example.madpt.API.diet.diet_list

import com.example.madpt.ModifyFoodData
import com.example.madpt.databinding.ActivityCustomFoodDataModifySaveBinding
import com.example.madpt.databinding.ActivityDietPageBinding

private lateinit var binding: ActivityCustomFoodDataModifySaveBinding

class CustomFoodDataModifySaveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustomFoodDataModifySaveBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.foodModifySaveButton.setOnClickListener() {
            val SaveFoodData = diet_list(
//              food_id = room.food_id,
                food_id = -1,
                food_name = binding.modifyFoodName.getText().toString(),
                weight = binding.modifyFoodGram.getText().toString().toDouble(),
                diet_kcal = binding.modifyFoodKcal.getText().toString().toDouble(),
                unit = binding.modifyUnit.getText().toString(),
                count = binding.modifyFoodCount.getText().toString().toInt(),
                is_custom = true
            )
            AddFoodList.add(SaveFoodData)
            finish()
        }
    }
}