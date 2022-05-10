package com.example.madpt.social

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.FriendsData
import com.example.madpt.R
import com.example.madpt.training.LoadRecyclerAdapter
import com.example.madpt.training.TrainingAdapter
import kotlinx.coroutines.NonDisposableHandle.parent

class FriendsListAdapter(private val context: Context) : RecyclerView.Adapter<FriendsListAdapter.ViewHolder>() {


    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.frieds_list, viewGroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}