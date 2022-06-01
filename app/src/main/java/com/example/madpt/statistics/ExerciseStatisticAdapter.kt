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
    
    private val idToImage= mapOf("PUSH UP" to R.drawable.pushup,
        "SQUAT" to R.drawable.standing,
        "LUNGE" to R.drawable.lunge,
        "SHOULDER PRESS" to R.drawable.shoulder_press,
        "MOUNTAIN CLIMBING" to R.drawable.mountain_climbing,
        "SIDE LATERAL RAISE" to R.drawable.side_lateral_raise,
        "SIDE LUNGE" to R.drawable.side_lunge,
        "DUMBEL CURL" to R.drawable.dumbbell_curl)

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val exerciseTitle: TextView = itemView.findViewById(R.id.exercise_title_text)
        private val exerciseReps: TextView = itemView.findViewById(R.id.exercise_rep_text)
        private val exerciseSets: TextView = itemView.findViewById(R.id.exercise_set_text)
        private val exerciseKcal: TextView = itemView.findViewById(R.id.exercise_kcal_text)
        private val exerciseImage: ImageView = itemView.findViewById(R.id.exercise_image)

        fun bind(data: TrainRecordList){
            exerciseImage.setImageResource(idToImage[data.exercise_id]!!)
            exerciseTitle.text = data.exercise_id + " "
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