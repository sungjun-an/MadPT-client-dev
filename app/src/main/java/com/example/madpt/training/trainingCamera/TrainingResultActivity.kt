package com.example.madpt.training.trainingCamera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.databinding.ActivityTrainingResultBinding
import com.example.madpt.testmodel
import com.example.madpt.training.trainingCamera.data.TrainingData
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.LineDataSet

class TrainingResultActivity : AppCompatActivity(), onTrainingResultClickLisner{

    private lateinit var binding : ActivityTrainingResultBinding
    private lateinit var chart: LineChart
    private lateinit var fixedTrainListRecyclerView: RecyclerView
    private var chartDataList = ArrayList<TrainingData>()
    private var trainingList = ArrayList<testmodel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainingResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        chartDataList = intent.getParcelableArrayListExtra<TrainingData>(
            "trainingDataList") as ArrayList<TrainingData>
        trainingList = intent.getParcelableArrayListExtra<testmodel>(
            "trainingList") as ArrayList<testmodel>

        Log.d("training list", trainingList.toString())

        binding.fixedTrainListRecyclerView.adapter = ResultAdapter(trainingList,
            this)
        binding.fixedTrainListRecyclerView.layoutManager = LinearLayoutManager(this).also {
            it.orientation = LinearLayoutManager.HORIZONTAL }
    }

    fun showChart(){
        val excrciseName = chartDataList

    }
}