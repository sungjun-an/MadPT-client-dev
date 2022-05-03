package com.example.madpt.diet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.madpt.AddFoodList
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
            val SaveFoodData = ModifyFoodData(
//              food_id = room.food_id,
                food_id = -1,
                food_name = binding.modifyFoodName.getText().toString(),
                maker_name = "",
                weight = binding.modifyFoodGram.getText().toString().toDouble(),
                kcal = binding.modifyFoodKcal.getText().toString().toDouble(),
                unit = binding.modifyUnit.getText().toString(),
                count = binding.modifyFoodCount.getText().toString().toInt()
            )
            AddFoodList.add(SaveFoodData)
            finish()
        }
    }
}