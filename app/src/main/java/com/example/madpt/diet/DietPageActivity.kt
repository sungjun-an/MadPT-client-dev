package com.example.madpt.diet

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.madpt.*
import com.example.madpt.databinding.ActivityDietPageBinding
import com.example.madpt.main.MainPageFragment
import java.time.LocalDateTime

class DietPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDietPageBinding


    var userData : UserData? = null
    var dataSumKcal : Int = 0
    var sumSimpleKcal : Int = 0
    var allSumKcal : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        val dietType : Bundle = intent.getBundleExtra("myBundle")!!

        super.onCreate(savedInstanceState)
        binding = ActivityDietPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sampleFoodList()
        simpleButton()
        val MainPageFragment = MainPageFragment()

        for(i in AddFoodList.indices){
            dataSumKcal += AddFoodList[i].kcal.toInt()
        }
        allSumKcal = dataSumKcal+sumSimpleKcal
        binding.sumKcal.setText(allSumKcal.toString())

        val foodListAdapter = FoodListViewAdapter(this, AddFoodList)
        binding.listview1.adapter = foodListAdapter

        binding.foodSearchIntentButton.setOnClickListener(){
            val intent = Intent(this,DietSearchActivity::class.java)
            startActivity(intent)
        }
        binding.dietSaveButton.setOnClickListener(){
            userDietData.add(UserData(Date = LocalDateTime.now(), diet_type = dietType.getString("diet_Type","") ,simple_total_kcal = sumSimpleKcal, diet_list = AddFoodList))
            val bundle = Bundle()
            bundle.putInt("SumKcal",allSumKcal)

            MainPageFragment.arguments = bundle

            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.fl_container,MainPageFragment)
            transaction.commit()
        }
    }

    override fun onRestart() {
        dataSumKcal = 0
        for(i in AddFoodList.indices){
            dataSumKcal += AddFoodList[i].kcal.toInt()
        }
        allSumKcal = dataSumKcal+sumSimpleKcal
        binding.sumKcal.setText(allSumKcal.toString())

        val foodListAdapter = FoodListViewAdapter(this, AddFoodList)
        binding.listview1.adapter = foodListAdapter
        super.onRestart()
    }



    fun simpleButton(){

        binding.simplePlus10Kcal.setOnClickListener(){
            sumSimpleKcal += 10
            allSumKcal = dataSumKcal+sumSimpleKcal
            binding.sumKcal.setText(allSumKcal.toString())
        }
        binding.simplePlus50Kcal.setOnClickListener(){
            sumSimpleKcal += 50
            allSumKcal = dataSumKcal+sumSimpleKcal
            binding.sumKcal.setText(allSumKcal.toString())
        }
        binding.simplePlus100Kcal.setOnClickListener(){
            sumSimpleKcal += 100
            allSumKcal = dataSumKcal+sumSimpleKcal
            binding.sumKcal.setText(allSumKcal.toString())
        }
        binding.simplePlus300Kcal.setOnClickListener(){
            sumSimpleKcal += 300
            allSumKcal = dataSumKcal+sumSimpleKcal
            binding.sumKcal.setText(allSumKcal.toString())
        }
        binding.simpleMinus10Kcal.setOnClickListener(){
            sumSimpleKcal -= 10
            allSumKcal = dataSumKcal+sumSimpleKcal
            binding.sumKcal.setText(allSumKcal.toString())
        }
        binding.simpleMinus50Kcal.setOnClickListener(){
            sumSimpleKcal -= 30
            allSumKcal = dataSumKcal+sumSimpleKcal
            binding.sumKcal.setText(allSumKcal.toString())
        }
        binding.simpleMinus100Kcal.setOnClickListener(){
            sumSimpleKcal -= 100
            allSumKcal = dataSumKcal+sumSimpleKcal
            binding.sumKcal.setText(allSumKcal.toString())
        }
        binding.simpleMinus300Kcal.setOnClickListener(){
            sumSimpleKcal -= 300
            allSumKcal = dataSumKcal+sumSimpleKcal
            binding.sumKcal.setText(allSumKcal.toString())
        }
    }
}