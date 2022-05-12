package com.example.madpt.social

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madpt.R
import com.example.madpt.databinding.FragmentMainPageBinding
import com.example.madpt.databinding.FragmentSocialBinding
import com.example.madpt.inputTestFriendsData
import java.text.SimpleDateFormat
import java.util.*


class SocialFragment : Fragment() {

    private var _binding : FragmentSocialBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inputTestFriendsData()
        _binding = FragmentSocialBinding.inflate(inflater, container, false)

        var calendar = Calendar.getInstance()


        var year = calendar.get(Calendar.YEAR).toString()
        val month = ((calendar.get(Calendar.MONTH))+1).toString()
        var week = calendar.get(Calendar.WEEK_OF_MONTH).toString()
        Log.d("날짜","${month}")

        var thisWeek = year + "년 " + month + "월 " + week + "주차"


        binding.dateThisWeek.text = thisWeek
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = context?.let { FriendsListAdapter(it) }


        return binding.root
    }

}