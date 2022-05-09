package com.example.madpt.diet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.core.widget.addTextChangedListener
import com.example.madpt.databinding.ActivitySearchFoodDataModifySaveBinding
import com.example.madpt.*
import com.example.madpt.API.diet.AddFoodList
import com.example.madpt.API.diet.diet_list
import com.example.madpt.API.food.food_list
import org.w3c.dom.Text
import java.lang.Math.round
import java.lang.NumberFormatException


class SearchFoodDataModifySaveActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchFoodDataModifySaveBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchFoodDataModifySaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var room = intent.getSerializableExtra("roomInfo") as food_list

        binding.modifyFoodName.setText(room.food_name)
        binding.modifyFoodKcal.text = room.food_data.defaultKcal.toString()
        binding.modifyCar.text = room.food_data.defaultCarbohydrate.toString()
        binding.modifyFat.text = room.food_data.defaultFat.toString()
        binding.modifyProtein.text = room.food_data.defaultProtein.toString()
        binding.modifyFoodCount.setText("1")
        binding.modifyFoodGram.setText(room.food_data.defaultWeight.toString())
        binding.oneTimeText.text = room.food_data.defaultWeight.toString()
        binding.modifyUnit.setText("개")

        binding.foodModifySaveButton.setOnClickListener() {
            val SaveFoodData = diet_list(
                food_id = room.food_id,
                food_name = room.food_name,
                diet_kcal = binding.modifyFoodKcal.getText().toString().toDouble(),
                weight = binding.modifyFoodGram.getText().toString().toDouble(),
                unit = binding.modifyUnit.getText().toString(),
                count = binding.modifyFoodCount.getText().toString().toInt()

            )
            AddFoodList.add(SaveFoodData)
            finish()
        }

        binding.modifyFoodGram.addTextChangedListener(object : TextWatcher {
            val initFoodGram : Double = room.food_data.defaultWeight
            val initFoodKcal : Double = room.food_data.defaultKcal
            val initFoodCar : Double = room.food_data.defaultCarbohydrate
            val initFoodProtein : Double = room.food_data.defaultProtein
            val initFoodFat : Double = room.food_data.defaultFat

            var inputFoodGram: Double? = 0.0
            var perFoodGram : Double? = 0.0


            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try {
                    inputFoodGram = binding.modifyFoodGram.getText().toString().toDouble()
                    perFoodGram = (inputFoodGram!! * 100.0) / initFoodGram
                }catch(e: NumberFormatException){
                    inputFoodGram = 0.0
                    perFoodGram = (inputFoodGram!! * 100.0) / initFoodGram
                }

                binding.modifyFoodKcal.setText((round(perFoodGram!!*initFoodKcal)/100.0).toString())
                binding.modifyProtein.setText((round(perFoodGram!!*initFoodProtein)/100.0).toString())
                binding.modifyFat.setText((round(perFoodGram!!*initFoodFat)/100.0).toString())
                binding.modifyCar.setText((round(perFoodGram!!*initFoodCar)/100.0).toString())
            }
        })
    }
}