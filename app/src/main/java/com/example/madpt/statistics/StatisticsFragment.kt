package com.example.madpt.statistics

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.example.madpt.R
import com.example.madpt.databinding.FragmentStatisticsBinding
import com.example.madpt.statistics.calendar.CalendarStartActivity
import com.example.madpt.statistics.calendar.setTextColorRes
import java.time.LocalDate
import java.time.ZoneId

class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!
    private var dailyDate = LocalDate.now()
    private var date = ("%02d월 %02d일").format(dailyDate.monthValue,dailyDate.dayOfMonth)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null) {
            val dailyDietStatisticsFragment = DailyDietStatisticsFragment()
            val bundle = Bundle()
            bundle.putLong("getTime",dailyDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
            dailyDietStatisticsFragment.arguments = bundle
            childFragmentManager.beginTransaction()
                .add(R.id.frag_container,dailyDietStatisticsFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        binding.btnDailyDiet.setBackgroundResource(R.drawable.bottom_stroke_select)
        binding.btnDailyDiet.setTextColorRes(R.color.puple)
        binding.textDate.text = date

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
        val dailyDietStatisticsFragment = DailyDietStatisticsFragment()
        val bundle = Bundle()
        binding.btnDailyDiet.setBackgroundResource(R.drawable.bottom_stroke_select)
        binding.btnDailyDiet.setTextColorRes(R.color.puple)
        binding.btnDailyExercise.setBackgroundResource(R.drawable.bottom_stroke)
        binding.btnDailyExercise.setTextColorRes(R.color.white)
        bundle.putLong("getTime", dailyDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        dailyDietStatisticsFragment.arguments = bundle
        childFragmentManager.beginTransaction()
            .replace(R.id.frag_container,dailyDietStatisticsFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    private fun setExerciseView(){
        val dailyExerciseStatisticsFragment = DailyExerciseStatisticsFragment()
        val bundle = Bundle()
        binding.btnDailyExercise.setBackgroundResource(R.drawable.bottom_stroke_select)
        binding.btnDailyExercise.setTextColorRes(R.color.puple)
        binding.btnDailyDiet.setBackgroundResource(R.drawable.bottom_stroke)
        binding.btnDailyDiet.setTextColorRes(R.color.white)
        bundle.putLong("getTime", dailyDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
        dailyExerciseStatisticsFragment.arguments = bundle
        childFragmentManager.beginTransaction()
            .replace(R.id.frag_container, dailyExerciseStatisticsFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
