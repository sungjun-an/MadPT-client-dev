package com.example.madpt.training

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.API.routine.PostTrainRoutine
import com.example.madpt.R
import com.example.madpt.storeTraining

class LoadRecyclerAdapter(private val storeTrainList: ArrayList<PostTrainRoutine>, listen:OnItemClickListener): RecyclerView.Adapter<LoadRecyclerAdapter.ViewHolder>() {

    private val listen = listen

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val trainTitle = itemView.findViewById<TextView>(R.id.load_train_title)
        fun bind(data: PostTrainRoutine){
            trainTitle.text = data.routine_title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.load_frame, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listen.onClick(storeTrainList[position])
        }
        holder.bind(storeTrainList[position])
    }

    override fun getItemCount(): Int {
        return storeTrainList.size
    }
}