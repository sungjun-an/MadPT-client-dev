package com.example.madpt.statistics

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.example.madpt.R
import com.example.madpt.databinding.FragmentStatisticsBinding


class StatisticsFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

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
        binding.btnDailyDiet.setBackgroundColor(resources.getColor(R.color.material_dynamic_neutral_variant30))
        binding.btnDailyDiet.setOnClickListener {
            binding.btnDailyDiet.setBackgroundColor(resources.getColor(R.color.material_dynamic_neutral_variant30))
            binding.btnDailyExercise.setBackgroundColor(resources.getColor(R.color.meal_input))
            childFragmentManager.beginTransaction()
                .replace(R.id.frag_container,DailyDietStatisticsFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
        binding.btnDailyExercise.setOnClickListener {
            binding.btnDailyExercise.setBackgroundColor(resources.getColor(R.color.material_dynamic_neutral_variant30))
            binding.btnDailyDiet.setBackgroundColor(resources.getColor(R.color.meal_input))
            childFragmentManager.beginTransaction()
                .replace(R.id.frag_container,DailyExerciseStatisticsFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
