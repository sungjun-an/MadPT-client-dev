package com.example.madpt.statistics.calendar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentTransaction
import com.example.madpt.API.food.GetMonthDataList
import com.example.madpt.API.statistic.GetMonthDataCall
import com.example.madpt.API.statistic.MonthDataDateBy
import com.example.madpt.R
import com.example.madpt.databinding.ActivityCalendarStartBinding
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList

class CalendarStartActivity : AppCompatActivity(), GetMonthDataList {

    private lateinit var binding: ActivityCalendarStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var dailyDate = LocalDate.now()
        dailyDate = dailyDate.plusMonths(1)
        Log.d("YMC","$dailyDate")
        GetMonthDataCall(this, this).getMonthData(dailyDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
    }


    override fun getMonthDataList(monthDataList: ArrayList<MonthDataDateBy>) {
        var calendarFragment = CalendarFragment()
        var bundle = Bundle()
        bundle.putParcelableArrayList("monthDataList", monthDataList)
        calendarFragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, calendarFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}