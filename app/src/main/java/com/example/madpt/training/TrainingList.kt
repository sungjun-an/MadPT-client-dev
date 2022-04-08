package com.example.madpt.training

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.R
import com.example.madpt.testmodel

class TrainingList(private val context: Context, private val dataList:ArrayList<testmodel>): RecyclerView.Adapter<TrainingList.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val trainImage = itemView.findViewById<ImageView>(R.id.list_image)
        val cancelTrain = itemView.findViewById(R.id.button) as Button

        fun bind(data: testmodel){
            trainImage.setImageResource(data.images)
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.selected_train_list, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewholder: ViewHolder, i: Int) {
        viewholder.cancelTrain.setOnClickListener {

        }
        viewholder.bind(dataList[i])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}