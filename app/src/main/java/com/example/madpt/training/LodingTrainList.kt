package com.example.madpt.training

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.API.food.GetExerciseRoutineList
import com.example.madpt.API.routine.GetTrainRoutine
import com.example.madpt.API.routine.GetTrainRoutineCall
import com.example.madpt.API.routine.PostTrainRoutine
import com.example.madpt.R
import com.example.madpt.storeTraining

class LodingTrainList(context: Context, storeTrainList: ArrayList<storeTraining>, listen: OnItemClickListener): OnItemClickListener,GetExerciseRoutineList {
    private val context = context
    private val dialog = Dialog(context)
    private var loadTrainList: ArrayList<PostTrainRoutine>? = null

    private val listen = listen

    @SuppressLint("SetTextI18n")
    fun showDialog() {
        GetTrainRoutineCall(this,context).GetTrainRoutine()
        dialog.setContentView(R.layout.load_training_list)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()
    }

    override fun onClick(loadItem: PostTrainRoutine) {
        Log.d("YMC","$loadItem")
        listen.onClick(loadItem)
        dialog.dismiss()
    }

    override fun getExerciseRoutineList(exerciseRoutineList: GetTrainRoutine) {
        loadTrainList = exerciseRoutineList.routine_list
        dialog.findViewById<RecyclerView>(R.id.load_train_list)
            .layoutManager = LinearLayoutManager(context)
        dialog.findViewById<RecyclerView>(R.id.load_train_list)
            .adapter = LoadRecyclerAdapter(loadTrainList!!, this)
    }

}