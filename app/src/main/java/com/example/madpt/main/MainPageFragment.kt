package com.example.madpt.main

import android.app.ProgressDialog.show
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.madpt.MainActivity
import com.example.madpt.databinding.FragmentMainPageBinding
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



        binding.goalButton.setOnClickListener() {
            mainActivity!!.changeGoalSettingFragment()
        }

        binding.plusButtonWeight.setOnClickListener(){

        }




        return binding.root
    }
}