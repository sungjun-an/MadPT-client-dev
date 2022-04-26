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
import com.example.madpt.MainActivity
import com.example.madpt.databinding.FragmentMainPageBinding
import com.example.madpt.diet.DietPageActivity
import com.example.madpt.dietType
import com.example.madpt.dietType1
import com.example.madpt.user

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



        dietType.add(dietType1)

        var sumKcalBreakfast: Int = 0
        var sumKcalLunch: Int = 0
        var sumKcalDinner: Int = 0
        var sumKcalSnack: Int = 0

        for (x in 0 until 1) {
            if (dietType[x].user_id == user.user_id) {

                when (dietType[x].type) {
                    0 -> sumKcalBreakfast = dietType[x].kcal + sumKcalBreakfast
                    1 -> sumKcalLunch = dietType[x].kcal + sumKcalLunch
                    2 -> sumKcalDinner = dietType[x].kcal + sumKcalDinner
                    3 -> sumKcalSnack = dietType[x].kcal + sumKcalSnack
                }
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
            intent.putExtra("diet_type","Breakfast")
            startActivity(intent)
        }//DietPageActicity로의 화면전환


        return binding.root
    }
}