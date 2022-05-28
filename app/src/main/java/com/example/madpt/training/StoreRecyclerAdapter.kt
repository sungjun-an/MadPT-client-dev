package com.example.madpt.training

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.R
import com.example.madpt.testmodel

class StoreRecyclerAdapter(private val dataList:ArrayList<testmodel>): RecyclerView.Adapter<StoreRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val trainImage = itemView.findViewById<ImageView>(R.id.trainImage)
        private val trainTitle = itemView.findViewById<TextView>(R.id.trainID)
        private val trainSet = itemView.findViewById<TextView>(R.id.trainSet)
        private val trainRep = itemView.findViewById<TextView>(R.id.trainRep)

        fun bind(data: testmodel){
            trainImage.setImageResource(data.images)
            trainTitle.text = data.titles
            trainSet.text = data.sets.toString()
            trainRep.text = data.reps.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreRecyclerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.store_trainlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}