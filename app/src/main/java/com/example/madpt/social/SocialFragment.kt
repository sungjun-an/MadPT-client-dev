package com.example.madpt.social

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madpt.API.food.GetSocialRank
import com.example.madpt.API.social.Friends
import com.example.madpt.API.social.FriendsDataList
import com.example.madpt.API.statistic.GetSocialRankCall

import com.example.madpt.databinding.FragmentSocialBinding
import java.util.*
import kotlin.collections.ArrayList


class SocialFragment : Fragment(), GetSocialRank {

    private var _binding : FragmentSocialBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var timestamp = System.currentTimeMillis()
        _binding = FragmentSocialBinding.inflate(inflater, container, false)
        GetSocialRankCall(this,requireContext()).getSocialRank(timestamp)
        Log.d("time","${timestamp}")
        var calendar = Calendar.getInstance()


        var year = calendar.get(Calendar.YEAR).toString()
        val month = ((calendar.get(Calendar.MONTH))+1).toString()
        var date = calendar.get(Calendar.DATE).toString()

        var thisWeek = year + "년 " + month + "월 " + date + "일"

        binding.dateThisWeek.text = thisWeek



        return binding.root
    }

    override fun getSocialRank(Rank: ArrayList<Friends>) {
        FriendsDataList = Rank
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = context?.let { FriendsListAdapter(it) }

    }
}