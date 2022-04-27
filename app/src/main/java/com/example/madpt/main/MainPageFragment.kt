package com.example.madpt.main

import android.app.ProgressDialog.show
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.madpt.*
import com.example.madpt.databinding.FragmentMainPageBinding
import com.example.madpt.diet.DietPageActivity

class MainPageFragment : Fragment() {


    private var _binding: FragmentMainPageBinding? = null
    private val binding get() = _binding!!

    var mainActivity: MainActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainPageBinding.inflate(inflater, container, false)

        var sumKcalBreakfast: Int = 0
        var sumKcalLunch: Int = 0
        var sumKcalDinner: Int = 0
        var sumKcalSnack: Int = 0

        for (x in userDietData.indices) {
            for(y in userDietData[x].diet_list.indices){
                Log.d("확인", "hi"+userDietData[x].diet_type)
            }}

        for (x in userDietData.indices) {
            for(y in userDietData[x].diet_list.indices)
                when (userDietData[x].diet_type) {
                    "Breakfast" -> sumKcalBreakfast += userDietData[x].diet_list[y].toString().toInt()
                    "Lunch" -> sumKcalLunch += userDietData[x].diet_list[y].toString().toInt()
                    "Dinner" -> sumKcalDinner += userDietData[x].diet_list[y].toString().toInt()
                    "Snack" -> sumKcalSnack += userDietData[x].diet_list[y].toString().toInt()

                }
            }

        if (sumKcalBreakfast != 0) {
            val sum: String = sumKcalBreakfast.toString() + " kcal"
            binding.plusButtonBreakfast.visibility = View.INVISIBLE
            binding.kcalText.visibility = View.VISIBLE
            binding.kcalText.text = sum
        }

        if (user.user_weight != 0.0){
            val userWeight = user.user_weight.toString() + "Kg"
            binding.plusButtonWeight.visibility = View.INVISIBLE
            binding.userWeight.visibility = View.VISIBLE
            binding.userWeight.text = userWeight
        }
        //유저 몸무게 있을 시 몸무게 출력

        if ((user.user_Goal_weight != 0) && (user.user_weight !=0.0)){
            val userGoalWeightRemain = (user.user_Goal_weight!!.toDouble()-user.user_weight!!.toDouble()).toString() + "kg"
            binding.postGoalDistance.visibility = View.INVISIBLE
            binding.goalDistance.visibility = View.VISIBLE
            binding.goalDistance.text = userGoalWeightRemain
        }
        //목표 몸무게 있을시 목표몸무게 - 유저몸무게 출력





        binding.goalButton.setOnClickListener() {
            mainActivity!!.changeGoalSettingFragment()
        }

        binding.plusButtonWeight.setOnClickListener(){
            mainActivity!!.showMessageDialog()
        }
        binding.userWeight.setOnClickListener(){
            mainActivity!!.showMessageDialog()
        }


        binding.plusButtonBreakfast.setOnClickListener(){
            val intent = Intent(context,DietPageActivity::class.java)
            val bundle = Bundle()

            bundle.putString("diet_Type","Breakfast")
            intent.putExtra("diet_type","Breakfast")
            intent.putExtra("myBundle",bundle)
            startActivity(intent)
        }//DietPageActicity로의 화면전환


        return binding.root
    }
}