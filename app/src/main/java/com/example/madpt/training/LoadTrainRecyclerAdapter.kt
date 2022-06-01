package com.example.madpt.training

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.API.routine.ExerciseList
import com.example.madpt.R
import com.example.madpt.testmodel

class LoadTrainRecyclerAdapter( private val loadItem:ArrayList<ExerciseList>): RecyclerView.Adapter<LoadTrainRecyclerAdapter.ViewHolder>() {

    private val exerciseImage = mapOf<Long, Int>(
        1.toLong() to R.drawable.pushup,
        2.toLong() to R.drawable.standing,
        3.toLong() to R.drawable.lunge,
        4.toLong() to R.drawable.shoulder_press,
        5.toLong() to R.drawable.mountain_climbing,
        6.toLong() to R.drawable.side_lateral_raise,
        7.toLong() to R.drawable.side_lunge,
        8.toLong() to R.drawable.dumbbell_curl)

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val trainImage = itemView.findViewById<ImageView>(R.id.result_exercise_image)

        fun bind(data: ExerciseList){
            trainImage.setImageResource(exerciseImage[data.exercise_id]!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.show_result_training_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return loadItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(loadItem[position])
    }
}