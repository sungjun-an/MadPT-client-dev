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
import org.w3c.dom.Text
import java.lang.Math.round
import java.lang.NumberFormatException


class SearchFoodDataModifySaveActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchFoodDataModifySaveBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchFoodDataModifySaveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var room = intent.getSerializableExtra("roomInfo") as FoodData

        binding.modifyFoodName.setText(room.food_name)
        binding.modifyCar.setText(room.default_kcal.toString())
        binding.modifyFat.setText(room.default_fat.toString())
        binding.modifyProtein.setText(room.default_fat.toString())
        binding.modifyFoodCount.setText("1")
        binding.modifyFoodGram.setText(room.default_weight.toString())
        binding.oneTimeText.text = room.default_weight.toString()
        binding.modifyUnit.setText("개")

        binding.foodModifySaveButton.setOnClickListener() {
            val SaveFoodData = ModifyFoodData(
                food_id = room.food_id,
                food_name = binding.modifyFoodName.getText().toString(),
                maker_name = room.maker_name,
                weight = binding.modifyFoodGram.getText().toString().toDouble(),
                kcal = binding.modifyFoodKcal.getText().toString().toDouble(),
                unit = binding.modifyUnit.getText().toString(),
                count = binding.modifyFoodCount.getText().toString().toInt()
            )
            AddFoodList.add(SaveFoodData)
            val dataIntent = Intent(this,DietPageActivity::class.java)
            startActivity(dataIntent)
        }

        binding.modifyFoodGram.addTextChangedListener(object : TextWatcher {
            val initFoodGram : Double = room.default_weight
            val initFoodKcal : Double = room.default_kcal
            val initFoodCar : Double = room.default_carbohydrate
            val initFoodProtein : Double = room.default_protein
            val initFoodFat : Double = room.default_fat

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