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


class SocialFragment : Fragment() {

    private var _binding : FragmentSocialBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inputTestFriendsData()
        _binding = FragmentSocialBinding.inflate(inflater, container, false)


        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = context?.let { FriendsListAdapter(it) }


        return binding.root
    }

}