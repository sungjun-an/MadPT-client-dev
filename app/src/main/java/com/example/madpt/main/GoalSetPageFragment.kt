package com.example.madpt.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.madpt.databinding.FragmentGoalSetPageBinding
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.madpt.API.goal.Goal
import com.example.madpt.API.goal.PostGoalCall
import com.example.madpt.MainActivity
import com.example.madpt.databinding.FragmentMainPageBinding
import com.example.madpt.user
import java.lang.NullPointerException
import java.lang.NumberFormatException


class GoalSetPageFragment : Fragment() {

    private var _binding: FragmentGoalSetPageBinding? = null
    private val binding get() = _binding!!



    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoalSetPageBinding.inflate(inflater, container, false)
        binding.goalEatkcal.setText(user.eat_kcal.toString())
        binding.goalMovekcal.setText(user.move_kcal.toString())
        binding.goalWeight.setText(user.user_Goal_weight.toString())

        binding.goalSaveButton.setOnClickListener {
            try {
                val goal = Goal(binding.goalEatkcal.text.toString().toDouble(),
                    binding.goalMovekcal.text.toString().toDouble(),
                    binding.goalWeight.text.toString().toDouble())
                    PostGoalCall(requireContext()).postGoalCall(goal)

            } catch (e: NumberFormatException) {
                Toast.makeText(requireActivity(), "정수만 입력해주시기 바랍니다.", Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }


}