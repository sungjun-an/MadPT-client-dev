package com.example.madpt.statistics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madpt.API.statistic.TrainRecord
import com.example.madpt.API.statistic.TrainRecordList
import com.example.madpt.databinding.FragmentDailyExerciseStatisticsBinding
import java.util.ArrayList

class DailyExerciseStatisticsFragment : Fragment() {

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exerciseData.add(TrainRecordList("SQUAT",0,10,10, 210.0))
        exerciseData.add(TrainRecordList("PUSHUP",0,5,6, 130.0))
        exerciseData.add(TrainRecordList("DUMBELL",0,10,10, 240.0))
        exerciseData.add(TrainRecordList("LUNGE",0,8,15, 270.0))
        binding.dailyExerciseRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.dailyExerciseRecyclerView.adapter = ExerciseStatisticAdapter(exerciseData)
    }
}