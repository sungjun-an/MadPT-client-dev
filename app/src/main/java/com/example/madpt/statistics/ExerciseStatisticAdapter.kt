package com.example.madpt.statistics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.API.statistic.TrainRecordList
import com.example.madpt.R

class ExerciseStatisticAdapter(private val dailyExerciseList: ArrayList<TrainRecordList>)
    : RecyclerView.Adapter<ExerciseStatisticAdapter.ViewHolder>() {

    private val idToName= mapOf(1 to "PUSH UP", 2 to "SQUAT", 3 to "LUNGE", 4 to "DUMBBELL")
    private val idToImage= mapOf(1 to R.drawable.pushup, 2 to R.drawable.standing, 3 to R.drawable.lunge, 4 to R.drawable.dumbell)

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val exerciseTitle: TextView = itemView.findViewById(R.id.exercise_title_text)
        private val exerciseReps: TextView = itemView.findViewById(R.id.exercise_rep_text)
        private val exerciseSets: TextView = itemView.findViewById(R.id.exercise_set_text)
        private val exerciseKcal: TextView = itemView.findViewById(R.id.exercise_kcal_text)
        private val exerciseImage: ImageView = itemView.findViewById(R.id.exercise_image)

        fun bind(data: TrainRecordList){
            exerciseImage.setImageResource(idToImage[data.exercise_id]!!)
            exerciseTitle.text = idToName[data.exercise_id] + " "
            exerciseReps.text = data.reps.toString()
            exerciseSets.text = data.sets.toString()
            exerciseKcal.text = data.kcal.toString()
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExerciseStatisticAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_daily_exercise_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseStatisticAdapter.ViewHolder, position: Int) {
        holder.bind(dailyExerciseList[position])
    }

    override fun getItemCount(): Int {
        return dailyExerciseList.size
    }

}