package com.example.madpt.training

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.API.food.GetExerciseRoutineList
import com.example.madpt.API.routine.ExerciseList
import com.example.madpt.API.routine.GetTrainRoutine
import com.example.madpt.API.routine.GetTrainRoutineCall
import com.example.madpt.API.routine.PostTrainRoutine
import com.example.madpt.R
import com.example.madpt.storeTraining

class LodingTrainList(context: Context, listen: OnItemClickListener): OnItemClickListener,GetExerciseRoutineList {
    private val context = context
    private val dialog = Dialog(context)
    private var loadTrainList: ArrayList<PostTrainRoutine>? = null
    private lateinit var routineLoadItem: PostTrainRoutine
    private val listen = listen

    @SuppressLint("SetTextI18n")
    fun showDialog() {
        GetTrainRoutineCall(this,context).GetTrainRoutine()
        dialog.setContentView(R.layout.load_training_list)
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        dialog.findViewById<Button>(R.id.routineCancel).setOnClickListener{
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.routineOk).setOnClickListener {
            listen.onClick(routineLoadItem)
            dialog.dismiss()
        }
    }

    override fun onClick(loadItem: PostTrainRoutine) {
        Log.d("YMC","$loadItem")
        routineLoadItem = loadItem
        dialog.findViewById<RecyclerView>(R.id.routineList)
            .layoutManager = LinearLayoutManager(context).also { it.orientation = LinearLayoutManager.HORIZONTAL }
        dialog.findViewById<RecyclerView>(R.id.routineList)
            .adapter = LoadTrainRecyclerAdapter(loadItem.exercise_list)
    }

    override fun getExerciseRoutineList(exerciseRoutineList: GetTrainRoutine) {
        loadTrainList = exerciseRoutineList.routine_list
        dialog.findViewById<RecyclerView>(R.id.load_train_list)
            .layoutManager = LinearLayoutManager(context)
        dialog.findViewById<RecyclerView>(R.id.load_train_list)
            .adapter = LoadRecyclerAdapter(loadTrainList!!, this)
    }

}