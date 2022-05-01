package com.example.madpt.training.trainingCamera

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.R
import com.example.madpt.testmodel
import com.example.madpt.training.OnRecyclerClickListener
import com.example.madpt.training.SetDialog
import com.example.madpt.training.TrainingAdapter
import com.example.madpt.training.trainingCamera.data.TrainingData

class ResultAdapter(private val trainingList:ArrayList<testmodel>, onTrainingResultClickLisner: onTrainingResultClickLisner) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder> (){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val result_training_Image =
            itemView.findViewById<ImageView>(R.id.result_exercise_image)

        fun bind(data: testmodel){
            result_training_Image.setImageResource(data.images) // testmodel의 image
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.show_result_training_list, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewholder: ViewHolder, i: Int) {
        viewholder.result_training_Image.setOnClickListener{
            println("누름")
        }
        viewholder.bind(trainingList[i])
    }

    override fun getItemCount(): Int {
        return trainingList.size
    }
}