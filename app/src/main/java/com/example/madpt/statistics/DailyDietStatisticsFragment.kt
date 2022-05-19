package com.example.madpt.statistics

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Dimension
import com.example.madpt.API.food.GetDailyDietList
import com.example.madpt.API.statistic.*
import com.example.madpt.R
import com.example.madpt.databinding.FragmentDailyDietStatisticsBinding
import kotlin.math.round

class DailyDietStatisticsFragment : Fragment(),GetDailyDietList {

    private var dailyDietList = arrayListOf<DailyDietList>()
    private var totalDietKcal = 0.0
    private var totalFoodData = arrayOf(0.0, 0.0, 0.0)
    private lateinit var _binding: FragmentDailyDietStatisticsBinding
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDailyDietStatisticsBinding.inflate(inflater, container, false)
        val dailyDate = arguments?.getLong("getTime")
        if (dailyDate != null) {
            GetDailyDietListCall(this, requireContext()).getDailyDietListCall(dailyDate)
        }
        return binding.root
    }

    private fun statisticsRate(){

        for(i in dailyDietList){
            var totalTypeKcal = 0.0
            totalDietKcal += i.simple_diet_kcal
            totalTypeKcal += i.simple_diet_kcal
            for(j in i.diet_list_by_type){
                totalFoodData[0] = totalFoodData[0] + j.food_data.default_car
                totalFoodData[1] = totalFoodData[1] + j.food_data.default_pro
                totalFoodData[2] = totalFoodData[2] + j.food_data.default_fat
                totalTypeKcal += j.diet_kcal
                totalDietKcal += j.diet_kcal
                createTextView(j.food_name, j.unit, j.count, j.diet_kcal, i.diet_type)
            }
            if (i.simple_diet_kcal != 0.0) {
                createTextView("간편 입력","" ,0, i.simple_diet_kcal, i.diet_type)
            }
            setTypeKcal(totalTypeKcal, i.diet_type)
        }
        val totalFoodDataSum = totalFoodData[0] + totalFoodData[1] + totalFoodData[2]

        binding.textDailyDietProtein.text = round((totalFoodData[1]/totalFoodDataSum*10.0)).toInt().toString()
        binding.textDailyDietProtein.layoutParams =
            LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,round((totalFoodData[1]/totalFoodDataSum*10.0)).toFloat())

        binding.textDailyDietCarbohydrate.text = round((totalFoodData[0]/totalFoodDataSum*10.0)).toInt().toString()
        binding.textDailyDietCarbohydrate.layoutParams =
            LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,round((totalFoodData[0]/totalFoodDataSum*10.0)).toFloat())

        binding.textDailyDietFat.text = round((totalFoodData[2]/totalFoodDataSum*10.0)).toInt().toString()
        binding.textDailyDietFat.layoutParams =
            LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,round((totalFoodData[2]/totalFoodDataSum*10.0)).toFloat())

        binding.textDailyDietKcal.text = totalDietKcal.toString()
    }

    private fun createTextView(name: String, unit: String, count: Int, foodKcal: Double, dietType: String){
        val height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25F, resources.displayMetrics).toInt()
        val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10F, resources.displayMetrics).toInt()
        val foodType:String
        if (unit == "" && count == 0) {
            foodType = "%s %.2f Kcal".format(name, foodKcal.toFloat())
        } else {
            foodType = "%s %d %s %.2f Kcal".format(name, count, unit, foodKcal.toFloat())
        }
        val newFoodText = TextView(context)
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height)
        layoutParams.setMargins(0,0,margin,0)
        newFoodText.layoutParams = layoutParams
        newFoodText.gravity = Gravity.RIGHT
        newFoodText.setTextColor(Color.WHITE)
        newFoodText.setTextSize(Dimension.DP,40F)
        newFoodText.text = foodType
        if(dietType == "Breakfast") {
            binding.breakfastTotalList.addView(newFoodText)
        } else if(dietType == "Lunch"){
            binding.lunchTotalList.addView(newFoodText)
        }else if(dietType == "Dinner"){
            binding.dinnerTotalList.addView(newFoodText)
        }else{
            binding.snackTotalList.addView(newFoodText)
        }
    }

    private fun setTypeKcal(totalTypeKcal: Double, dietType: String){
        if(dietType == "Breakfast") {
            binding.breakfastTotalKcal.text = totalTypeKcal.toString()
        } else if(dietType == "Lunch"){
            binding.lunchTotalKcal.text = totalTypeKcal.toString()
        }else if(dietType == "Dinner"){
            binding.dinnerTotalKcal.text = totalTypeKcal.toString()
        }else{
            binding.snackTotalKcal.text = totalTypeKcal.toString()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun getDailyDiet(DietList: DailyDietStatistic) {
        dailyDietList = DietList.dailyDietList
        statisticsRate()
    }
}