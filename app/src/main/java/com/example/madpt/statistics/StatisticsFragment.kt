package com.example.madpt.statistics

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.example.madpt.R
import com.example.madpt.databinding.FragmentStatisticsBinding
import com.example.madpt.statistics.calendar.CalendarStartActivity
import java.time.LocalDate

class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private var dailyDate = LocalDate.now()
    private var date = ("%02d월 %02d일").format(dailyDate.monthValue,dailyDate.dayOfMonth)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null) {
            childFragmentManager.beginTransaction()
                .add(R.id.frag_container,DailyDietStatisticsFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        binding.textDate.text = date
        binding.btnDailyDiet.setBackgroundColor(resources.getColor(R.color.material_dynamic_neutral_variant30))

        binding.btnDailyDiet.setOnClickListener {
            setDietView()
        }
        binding.btnDailyExercise.setOnClickListener {
            setExerciseView()
        }

        binding.nextDate.setOnClickListener {
            dailyDate = dailyDate.plusDays(1)
            if(dailyDate.dayOfMonth>dailyDate.lengthOfMonth()){
                dailyDate.plusMonths(1)
                if (dailyDate.monthValue>dailyDate.lengthOfYear()){
                    dailyDate.withDayOfMonth(1)
                }
                dailyDate.withDayOfMonth(1)
            }
            date = ("%02d월 %02d일").format(dailyDate.monthValue,dailyDate.dayOfMonth)
            binding.textDate.text = date
            setDietView()
        }

        binding.beforeDate.setOnClickListener {
            dailyDate = dailyDate.minusDays(1)
            if(dailyDate.dayOfMonth == 0){
                dailyDate.minusMonths(1)
                if (dailyDate.monthValue == 0) {
                    dailyDate.withDayOfMonth(12)
                }
                dailyDate.withDayOfMonth(dailyDate.monthValue)
            }
            date = ("%02d월 %02d일").format(dailyDate.monthValue,dailyDate.dayOfMonth)
            binding.textDate.text = date
            setDietView()
        }

        binding.setCalendarView.setOnClickListener {
            val intent = Intent(activity, CalendarStartActivity::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    private fun setDietView(){
        binding.btnDailyDiet.setBackgroundColor(resources.getColor(R.color.material_dynamic_neutral_variant30))
        binding.btnDailyExercise.setBackgroundColor(resources.getColor(R.color.meal_input))
        childFragmentManager.beginTransaction()
            .replace(R.id.frag_container,DailyDietStatisticsFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    private fun setExerciseView(){
        binding.btnDailyExercise.setBackgroundColor(resources.getColor(R.color.material_dynamic_neutral_variant30))
        binding.btnDailyDiet.setBackgroundColor(resources.getColor(R.color.meal_input))
        childFragmentManager.beginTransaction()
            .replace(R.id.frag_container,DailyExerciseStatisticsFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
