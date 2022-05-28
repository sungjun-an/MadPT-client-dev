package com.example.madpt.statistics

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madpt.API.food.GetTrainRecordList
import com.example.madpt.API.statistic.GetTrainRecordCall
import com.example.madpt.API.statistic.TrainRecordList
import com.example.madpt.databinding.FragmentDailyExerciseStatisticsBinding
import kotlin.collections.ArrayList

class DailyExerciseStatisticsFragment : Fragment(), GetTrainRecordList {

    private lateinit var _binding: FragmentDailyExerciseStatisticsBinding
    private val binding get() = _binding!!
    private var exerciseData = ArrayList<TrainRecordList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDailyExerciseStatisticsBinding.inflate(inflater, container, false)
        val dailyDate = arguments?.getLong("getTime")
        Log.d("YMC","$dailyDate")
        if (dailyDate !=null){
            GetTrainRecordCall(this,requireContext()).trainRecord(dailyDate)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.dailyExerciseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.dailyExerciseRecyclerView.adapter = ExerciseStatisticAdapter(exerciseData)
    }

    private fun sumExKcal(exerciseDataList: ArrayList<TrainRecordList>){
        var sum = 0.0
        for (i in exerciseDataList) {
            sum += i.kcal
        }

        binding.textDailyExerciseKcal.text = sum.toString()
    }

    override fun getTrainRecord(trainRecord: ArrayList<TrainRecordList>) {
        exerciseData.addAll(trainRecord)
        sumExKcal(exerciseData)
        binding.dailyExerciseRecyclerView.adapter?.notifyDataSetChanged()
    }
}