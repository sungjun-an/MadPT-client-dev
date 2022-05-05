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


class ResultAdapter(private val trainingList:ArrayList<testmodel>,
                    private val chartDataList: ArrayList<TrainingData>,
                    onTrainingResultClickLisner: onTrainingResultClickLisner) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder> (){

    private val clickListener = onTrainingResultClickLisner
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
            clickListener.viewSingleChart(chartDataList, i)
            clickListener.viewSingleReview(chartDataList, i)
        }
        viewholder.bind(trainingList[i])
    }

    override fun getItemCount(): Int {
        return trainingList.size
    }
}