package com.example.madpt.training.trainingCamera

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.databinding.ActivityTrainingResultBinding
import com.example.madpt.testmodel
import com.example.madpt.training.trainingCamera.data.TrainingData
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter

class TrainingResultActivity : AppCompatActivity(), onTrainingResultClickLisner{

    private lateinit var binding : ActivityTrainingResultBinding
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

        binding.fixedTrainListRecyclerView.adapter = ResultAdapter(trainingList, chartDataList,
            this)
        binding.fixedTrainListRecyclerView.layoutManager = LinearLayoutManager(this).also {
            it.orientation = LinearLayoutManager.HORIZONTAL }
        showMainChart()
    }

    fun showMainChart(){
        val excrcise_chart = ArrayList<Entry>();
        val repsAndSetsData = ArrayList<Int>()

        for(i in 0 until trainingList.size){
            repsAndSetsData.add(i, (trainingList[i].reps * trainingList[i].sets))
        }

        println(chartDataList.size)
        println(chartDataList)

        var sumData = 0
        var index = 0

        var singleExcrciseCount = 0
        var singleExcrciseScore = 0

        for(i in 0 until repsAndSetsData.size){
            if(i > 0){
                sumData += repsAndSetsData[i-1]
            }

            for(j in 0 until repsAndSetsData[i]){
                if (i == 0){
                    index = j
                    singleExcrciseCount = chartDataList[index].excrciseCount
                    singleExcrciseScore = chartDataList[index].excrciseScore
                }
                else{
                    index = j + sumData
                    singleExcrciseCount = chartDataList[index].excrciseCount + sumData
                    singleExcrciseScore = chartDataList[index].excrciseScore
                }

                excrcise_chart.add(Entry(
                    singleExcrciseCount.toFloat(), singleExcrciseScore.toFloat()))
            }
        }

        val excrciseChartDataset = LineDataSet(excrcise_chart, "자세 점수")
        val chartLineData = LineData(excrciseChartDataset)

        setChartOptions(chartLineData)
    }

    fun setChartOptions(chartLineData: LineData){
        val resultChart = binding.resultChart
        val resultChartLegend = binding.resultChart.legend

        val xAxis = resultChart.xAxis
        val yAxisLeft = resultChart.axisLeft
        val yAxisRight = resultChart.axisRight

        resultChartLegend.formSize = 5f
        resultChartLegend.textColor = Color.WHITE

        yAxisRight.isEnabled = false

        yAxisLeft.run{
            isEnabled = false
            mAxisMaximum = 40F
            mAxisMinimum = 0F
            setDrawGridLines(true)
            setDrawAxisLine(true)
        }

        xAxis.run{
            position = XAxis.XAxisPosition.BOTTOM
            isGranularityEnabled = true
            granularity = 1f
            valueFormatter = object: ValueFormatter(){
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString()
                }
            }
            setDrawAxisLine(true)
            setDrawGridLines(true)
            gridLineWidth = 1.5f
            spaceMax = 0.3f
            spaceMin = 0.3f
        }

        resultChart.run{
            isDragEnabled = true
            description.isEnabled = false
            isScaleXEnabled = true
            isScaleYEnabled = true
            data = chartLineData;
            invalidate();
        }
    }

    override fun viewSingleChart(singleExerciseData: TrainingData) {
        println("누누누누르므르름")
    }


}