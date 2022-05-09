package com.example.madpt.social

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.FriendsData
import com.example.madpt.R
import com.example.madpt.training.LoadRecyclerAdapter
import com.example.madpt.training.TrainingAdapter

class FriendsListAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.frieds_list,parent,false)

    }

}