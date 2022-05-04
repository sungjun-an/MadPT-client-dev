package com.example.madpt.diet

import android.content.Intent
import android.graphics.PointF.length
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.AdapterView
import androidx.core.util.ObjectsCompat.toString
import com.example.madpt.*
import com.example.madpt.API.food.GetFoodList
import com.example.madpt.API.food.GetFoodListCall
import com.example.madpt.API.food.food_list
import com.example.madpt.databinding.ActivityDietSearchBinding


class DietSearchActivity : AppCompatActivity(), GetFoodList {
    private lateinit var binding: ActivityDietSearchBinding

    private var food_search_result_list = ArrayList<food_list>()

    private var viewList = ArrayList<food_list>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDietSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewList.addAll(food_search_result_list)
        setupEvents()

        binding.foodSearchListview.setOnItemClickListener{parent,view,position,id ->
            val clickedRoom = food_search_result_list[position]
            val mIntent = Intent(this,SearchFoodDataModifySaveActivity::class.java)
            mIntent.putExtra("roomInfo",clickedRoom)
            startActivity(mIntent)
            finish()
        }
    }

    fun setupEvents() {

        binding.foodSearchButton.setOnClickListener() {
            var textlengh: Int = binding.foodSearchBar.text.length
            food_search_result_list.clear()
            var foodSearchText = binding.foodSearchBar.text.toString()

            GetFoodListCall(this).Get(foodSearchText)

//            for (i in FoodList.indices) {
//                var temp_food_maker: String?
//                if (FoodList[i].maker_name.toString()
//                        .contains(foodSearchText) || FoodList[i].food_name.contains(foodSearchText)
//                ) {
//                    if (FoodList[i].maker_name.equals("")) {
//                        temp_food_maker = FoodList[i].maker_name
//                    } else {
//                        temp_food_maker = "[" + FoodList[i].maker_name + "]"
//                    }
//                    food_search_result_list.add(
//                        FoodData(
//                            food_id = FoodList[i].food_id,
//                            food_name = FoodList[i].food_name,
//                            maker_name = temp_food_maker,
//                            default_kcal = FoodList[i].default_kcal,
//                            default_weight = FoodList[i].default_weight,
//                            default_carbohydrate = FoodList[i].default_carbohydrate,
//                            default_fat = FoodList[i].default_fat,
//                            default_protein = FoodList[i].default_protein
//                        )
//                    )
//                }
//            }
            val foodListAdapter = FoodSearchListViewAdapter(this, food_search_result_list)
            binding.foodSearchListview.adapter = foodListAdapter
        }
    }

    override fun getFoodList(testing: ArrayList<food_list>) {
        food_search_result_list = testing
        binding.foodSearchListview.adapter=FoodSearchListViewAdapter(this, food_search_result_list)
    }
}





