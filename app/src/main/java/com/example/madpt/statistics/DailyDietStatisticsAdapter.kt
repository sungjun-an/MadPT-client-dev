package com.example.madpt.statistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.R

class DailyDietStatisticsAdapter(): RecyclerView.Adapter<DailyDietStatisticsAdapter.ViewHolder> () {

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        private val dietType = itemView.findViewById<TextView>(R.id.text_diet_type)
        private val dietTypeKcal = itemView.findViewById<TextView>(R.id.text_diet_type_total)
        private val dietTypeRecycler = itemView.findViewById<RecyclerView>(R.id.diet_type_recycler_view)

        //bind 안에서 어뎁터로 한번 더 들어가기
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.daily_diet_recycler_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return 1
    }
}