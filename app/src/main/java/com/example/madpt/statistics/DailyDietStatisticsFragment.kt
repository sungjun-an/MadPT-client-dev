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
import com.example.madpt.API.statistic.DailyDietList
import com.example.madpt.API.statistic.DietListByType
import com.example.madpt.API.statistic.FoodData
import com.example.madpt.R
import com.example.madpt.databinding.FragmentDailyDietStatisticsBinding
import kotlin.math.round

class DailyDietStatisticsFragment : Fragment() {

    private var dietListByType= arrayListOf<DietListByType>()
    private var dietListByType1= arrayListOf<DietListByType>()
    private var dietListByType2= arrayListOf<DietListByType>()
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
        dietListByType.add(DietListByType("제육", 200.0, 100.0, 1,"접시", false, FoodData(32.0,49.0,53.0)))
        dietListByType.add(DietListByType("계란", 80.0, 25.0, 1,"개", false, FoodData(32.0,49.0,53.0)))
        dietListByType.add(DietListByType("밥", 230.0, 100.0, 1,"공기", false, FoodData(32.0,49.0,53.0)))
        dietListByType1.add(DietListByType("돈까스", 320.0, 150.0, 1,"개", false, FoodData(29.0,60.0,20.0)))
        dietListByType2.add(DietListByType("국밥", 300.0, 160.0, 1,"그릇", true, FoodData(24.0,40.0,21.0)))
        dailyDietList.add(DailyDietList("아침", 500.0, dietListByType))
        dailyDietList.add(DailyDietList("점심", 500.0, dietListByType1))
        dailyDietList.add(DailyDietList("저녁", 500.0, dietListByType2))
        statisticsRate()
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
            setTypeKcal(totalTypeKcal, i.diet_type)
        }
        val totalFoodDataSum = totalFoodData[0] + totalFoodData[1] + totalFoodData[2]

        binding.textDailyDietProtein.text = round((totalFoodData[1]/totalFoodDataSum*100.0)).toInt().toString()
        binding.textDailyDietProtein.layoutParams =
            LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,round((totalFoodData[1]/totalFoodDataSum*100.0)).toFloat())

        binding.textDailyDietCarbohydrate.text = round((totalFoodData[0]/totalFoodDataSum*100.0)).toInt().toString()
        binding.textDailyDietCarbohydrate.layoutParams =
            LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,round((totalFoodData[0]/totalFoodDataSum*100.0)).toFloat())

        binding.textDailyDietFat.text = round((totalFoodData[2]/totalFoodDataSum*100.0)).toInt().toString()
        binding.textDailyDietFat.layoutParams =
            LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,round((totalFoodData[2]/totalFoodDataSum*100.0)).toFloat())

        binding.textDailyDietKcal.text = totalDietKcal.toString()
    }

    private fun createTextView(name: String, unit: String, count: Int, foodKcal: Double, dietType: String){
        val height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25F, resources.displayMetrics).toInt()
        val margin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10F, resources.displayMetrics).toInt()
        val foodType = "%s %d %s %.2f Kcal".format(name, count, unit, foodKcal.toFloat())
        val newFoodText = TextView(context)
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height)
        layoutParams.setMargins(0,0,margin,0)
        newFoodText.layoutParams = layoutParams
        newFoodText.gravity = Gravity.RIGHT
        newFoodText.setTextColor(Color.WHITE)
        newFoodText.setTextSize(Dimension.DP,40F)
        newFoodText.text = foodType
        if(dietType == "아침") {
            binding.breakfastTotalList.addView(newFoodText)
        } else if(dietType == "점심"){
            binding.breakfastTotalList.addView(newFoodText)
        }else if(dietType == "저녁"){
            binding.dinnerTotalList.addView(newFoodText)
        }else{
            binding.snackTotalList.addView(newFoodText)
        }
    }

    private fun setTypeKcal(totalTypeKcal: Double, dietType: String){
        if(dietType == "아침") {
            binding.breakfastTotalKcal.text = totalTypeKcal.toString()
        } else if(dietType == "점심"){
            binding.lunchTotalKcal.text = totalTypeKcal.toString()
        }else if(dietType == "저녁"){
            binding.dinnerTotalKcal.text = totalTypeKcal.toString()
        }else{
            binding.snackTotalKcal.text = totalTypeKcal.toString()
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}