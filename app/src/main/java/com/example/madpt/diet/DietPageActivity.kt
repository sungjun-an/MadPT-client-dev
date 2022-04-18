package com.example.madpt.diet

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.madpt.*
import com.example.madpt.databinding.ActivityDietPageBinding

class DietPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDietPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDietPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sampleFoodList()
        sampleAddFoodList()

        val foodListAdapter = FoodListViewAdapter(this, AddFoodList)
        binding.listview1.adapter = foodListAdapter

        binding.foodSearchIntentButton.setOnClickListener(){
            val intent = Intent(this,DietSearchActivity::class.java)
            startActivity(intent)
        }

    }
}