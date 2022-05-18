package com.example.madpt.training.trainingCamera

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.R
import com.example.madpt.testmodel
import com.example.madpt.training.trainingCamera.data.TrainingData

class reviewAdapter(private val trainingList:ArrayList<testmodel>,
                    private val chartDataList: ArrayList<TrainingData>,
                    onTrainingReviewListener: onTrainingReviewListener):
    RecyclerView.Adapter<reviewAdapter.ViewHolder> (){

    private val reviewListener = onTrainingReviewListener
    private var totalGrade = getTotalGrade()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val reviewText: TextView =
            itemView.findViewById(R.id.review_excrcise)
        private val colon: TextView =
            itemView.findViewById(R.id.review_colon)
        private val reviewAverage: TextView =
            itemView.findViewById(R.id.review_average)

        fun Bind(data: testmodel, index: Int){
            reviewText.text = data.titles
            colon.text = ":"
            reviewAverage.text = totalGrade[index]
        }
    }

    private fun getTotalGrade(): ArrayList<String>{
        val scoreData = mutableListOf<Double>()

        for(i in 0 until trainingList.size){
            var sum = 0.0

            if(i == 0){
                val startIndex = trainingList[i].reps * trainingList[i].sets
                for (j in 0 until startIndex){
                    sum += chartDataList[j].excrciseScore
                }
                scoreData.add(i, (sum / startIndex))
            }
            else{
                var startIndex = 0
                val totalReps = trainingList[i].reps * trainingList[i].sets
                for(j in 0 until i){
                    startIndex += trainingList[j].reps * trainingList[j].sets
                }
                val untilIndex = totalReps + startIndex
                for(j in startIndex until untilIndex){
                    sum += chartDataList[j].excrciseScore
                }
                scoreData.add(i, (sum / totalReps))
            }
        }

        val finalGradeList: ArrayList<String> = ArrayList()

        for(i in 0 until scoreData.size){
            when {
                scoreData[i] < 70 -> {
                    finalGradeList.add(i, "D")
                }
                scoreData[i] < 80 -> {
                    finalGradeList.add(i, "C")
                }
                scoreData[i] < 90 -> {
                    finalGradeList.add(i, "B")
                }
                else -> {
                    finalGradeList.add(i, "A")
                }
            }
        }

        return finalGradeList
    }


    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): reviewAdapter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.show_review_list, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewholder: reviewAdapter.ViewHolder, i: Int) {
        viewholder.Bind(trainingList[i], i)
    }

    override fun getItemCount(): Int {
        return trainingList.size
    }

}
