package com.example.madpt.training.trainingCamera

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madpt.MainActivity
import com.example.madpt.R
import com.example.madpt.databinding.ActivityTrainingResultBinding
import com.example.madpt.testmodel
import com.example.madpt.training.trainingCamera.data.TrainingData
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter

class TrainingResultActivity : AppCompatActivity(), onTrainingResultClickLisner, onTrainingReviewListener{

    private lateinit var binding : ActivityTrainingResultBinding
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
        binding.AverageListRecyclerView.adapter = reviewAdapter(trainingList, chartDataList,
            this)
        binding.fixedTrainListRecyclerView.layoutManager = LinearLayoutManager(this).also {
            it.orientation = LinearLayoutManager.HORIZONTAL }
        binding.AverageListRecyclerView.layoutManager = LinearLayoutManager(this).also {
            it.orientation = LinearLayoutManager.VERTICAL }
        binding.exitBtn.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        binding.resultBtn.setOnClickListener {
            showMainChart()
            binding.AverageListRecyclerView.adapter = reviewAdapter(trainingList, chartDataList,
                this)
            binding.AverageListRecyclerView.layoutManager = LinearLayoutManager(this).also {
                it.orientation = LinearLayoutManager.VERTICAL }
        }
        showMainChart()
    }

    private fun showMainChart(){
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
                    singleExcrciseCount.toFloat(), singleExcrciseScore.toFloat()
                ))
            }
        }

        val excrciseChartDataset = LineDataSet(excrcise_chart, "자세 점수")
        val chartLineData = LineData(excrciseChartDataset)
        val flag = "main"

        val finalGradeList = getTotalGrade()
        showGradeIcon(finalGradeList)

        setChartOptions(chartLineData, flag, excrcise_chart)
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
                val untilIndex = trainingList[i].reps * trainingList[i].sets + startIndex
                for(j in startIndex until untilIndex){
                    sum += chartDataList[j].excrciseScore
                }
                scoreData.add(i, (sum / totalReps))
            }
        }

        val finalGradeList: ArrayList<String> = ArrayList()

        for(i in 0 until scoreData.size){
            when {
                scoreData[i] < 20 -> {
                    finalGradeList.add(i, "D")
                }
                scoreData[i] < 30 -> {
                    finalGradeList.add(i, "C")
                }
                scoreData[i] < 35 -> {
                    finalGradeList.add(i, "B")
                }
                else -> {
                    finalGradeList.add(i, "A")
                }
            }
        }

        return finalGradeList
    }

    fun showGradeIcon(finalGradeList: ArrayList<String>){
        var sum = 0.0
        for(i in 0 until finalGradeList.size){
            when {
                finalGradeList[i] == "D" -> {
                    sum += 0
                }
                finalGradeList[i] == "C" -> {
                    sum += 1
                }
                finalGradeList[i] == "B" -> {
                    sum += 2
                }
                else -> {
                    sum += 3
                }
            }
        }

        val average = sum / finalGradeList.size
        println(average)

        when{
            average in 0.0..0.999 -> {
                binding.resultScore.setImageResource(R.drawable.d_score)
            }
            average in 1.0..1.999 -> {
                binding.resultScore.setImageResource(R.drawable.c_score)
            }
            average in 2.0..2.999 -> {
                binding.resultScore.setImageResource(R.drawable.b_score)
            }
            average >= 3.0 -> {
                binding.resultScore.setImageResource(R.drawable.a_score)
            }
        }
    }

    private fun setChartOptions(chartLineData: LineData,
                                flag: String,
                                excrciseChart: ArrayList<Entry>){
        val resultChart = binding.resultChart
        val resultChartLegend = binding.resultChart.legend
        val totalReps = excrciseChart.size

        val xAxis = resultChart.xAxis
        val yAxisLeft = resultChart.axisLeft
        val yAxisRight = resultChart.axisRight

        resultChartLegend.formSize = 5f
        resultChartLegend.textColor = Color.WHITE

        yAxisRight.isEnabled = false

        yAxisLeft.run{
            isEnabled = false
            axisMaximum = 100F
            axisMinimum = 0F
            setDrawGridLines(true)
            setDrawAxisLine(true)
        }

        if (flag == "main"){
            xAxis.run{
                position = XAxis.XAxisPosition.BOTTOM
                isGranularityEnabled = true
                granularity = 1f
                labelCount = totalReps
                axisMinimum = 1f
                axisMaximum = totalReps.toFloat()
                valueFormatter = object: ValueFormatter(){
                    override fun getFormattedValue(value: Float): String {
                        val indexList = ArrayList<Int>()
                        for(i in 0 until trainingList.size){
                            if(i == 0){
                                indexList.add(0, 1)
                            }
                            else{
                                val startIndex =
                                    trainingList[i-1].reps * trainingList[i-1].sets + indexList[i-1]
                                indexList.add(i, startIndex)
                            }
                        }

                        for(i in 0 until indexList.size){
                            if(value.toInt() == indexList[i]) {
                                return trainingList[i].titles
                            }
                        }

                        return value.toInt().toString()
                    }
                }
                setDrawAxisLine(true)
                setDrawGridLines(true)
                gridLineWidth = 1.5f
                spaceMax = 0.3f
                spaceMin = 0.3f
            }
        }
        else{
            xAxis.run{
                position = XAxis.XAxisPosition.BOTTOM
                isGranularityEnabled = true
                granularity = 1f
                axisMinimum = 1f
                axisMaximum = totalReps.toFloat()
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

    override fun viewSingleChart(chartDataList: ArrayList<TrainingData>, i: Int) {
        val single_excrcise_chart = ArrayList<Entry>();

        if (i == 0){
            val untilIndex = trainingList[i].reps * trainingList[i].sets
            for(k in 0 until untilIndex){
                val singleExcrciseCount = chartDataList[k].excrciseCount
                val singleExcrciseScore = chartDataList[k].excrciseScore

                single_excrcise_chart.add(Entry(
                    singleExcrciseCount.toFloat(), singleExcrciseScore.toFloat()))
            }
        }
        else{
            var startIndex = 0
            for(k in 0 until i){
                startIndex += trainingList[k].reps * trainingList[k].sets
            }
            val untilIndex = trainingList[i].reps * trainingList[i].sets + startIndex
            for(k in startIndex until untilIndex){
                val singleExcrciseCount = chartDataList[k].excrciseCount
                val singleExcrciseScore = chartDataList[k].excrciseScore

                single_excrcise_chart.add(Entry(
                    singleExcrciseCount.toFloat(), singleExcrciseScore.toFloat()))
            }
        }

        val excrciseChartDataset = LineDataSet(single_excrcise_chart, "자세 점수")
        val chartLineData = LineData(excrciseChartDataset)
        val flag = "single"

        setChartOptions(chartLineData, flag, single_excrcise_chart)
    }

    override fun viewSingleReview(chartDataList: ArrayList<TrainingData>, i: Int) {
        binding.AverageListRecyclerView.adapter = singleReviewAdapter(trainingList,
            chartDataList,
            i,
            this)
    }
}