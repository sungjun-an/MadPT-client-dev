package com.example.madpt.training.trainingCamera

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.testmodel
import android.view.View
import android.widget.TextView
import com.example.madpt.R
import com.example.madpt.training.trainingCamera.data.TrainingData
import com.github.mikephil.charting.data.Entry

class singleReviewAdapter(private val trainingList: ArrayList<testmodel>,
                          private val chartDataList: ArrayList<TrainingData>,
                          private val excrciseIndex: Int,
                          onTrainingResultClickLisner: onTrainingResultClickLisner):
    RecyclerView.Adapter<singleReviewAdapter.ViewHolder> (){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val reviewText: TextView =
            itemView.findViewById(R.id.review_excrcise)
        private val colon: TextView =
            itemView.findViewById(R.id.review_colon)
        private val reviewAverage: TextView =
            itemView.findViewById(R.id.review_average)

        private val setsGrade = getSetGrade()

        fun Bind(data: testmodel){
            reviewText.text = data.titles
            colon.text = ":"
            reviewAverage.text = setsGrade
        }
    }

    fun getSetGrade(): String{
        var scoreData = 0.0
        var finalGrade = ""

        val totalReps = trainingList[excrciseIndex].reps * trainingList[excrciseIndex].sets
        var sum = 0.0

        if(excrciseIndex == 0){
            for(i in 0 until totalReps){
                sum += chartDataList[i].excrciseScore
            }

            scoreData = sum / totalReps
        }
        else{
            var startIndex = 0
            for(k in 0 until excrciseIndex){
                startIndex += trainingList[k].reps * trainingList[k].sets
            }
            val untilIndex = totalReps + startIndex

            for(k in startIndex until untilIndex){
                val singleExcrciseScore = chartDataList[k].excrciseScore
                sum += singleExcrciseScore
            }
            scoreData = sum / totalReps
        }

        finalGrade = when {
            scoreData < 50 ->{
                "F"
            }
            scoreData < 60 -> {
                "D"
            }
            scoreData < 75 -> {
                "C"
            }
            scoreData < 85 -> {
                "B"
            }
            else -> {
                "A"
            }
        }

        return finalGrade
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.show_review_list, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewholder: ViewHolder, i: Int) {
        viewholder.Bind(trainingList[excrciseIndex])
    }

    override fun getItemCount(): Int {
        return 1
    }

}