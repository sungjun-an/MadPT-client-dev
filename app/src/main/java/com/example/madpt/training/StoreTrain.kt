package com.example.madpt.training

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.API.routine.ExerciseList
import com.example.madpt.API.routine.PostTrainRoutine
import com.example.madpt.API.routine.PostTrainRoutineCall
import com.example.madpt.R
import com.example.madpt.testmodel

class StoreTrain(context: Context, breakTime: Int){

    private val context = context
    private val dialog = Dialog(context)
    private val breakTime = breakTime
<<<<<<< Updated upstream
    private val exerciseId = mapOf<String, Long>("PUSH UP" to 1, "SQUAT" to 2, "LUNGE" to 3, "DUMBBELL" to 4)
=======
    private val exerciseId = mapOf<String, Long>(
        "PUSH UP" to 1,
        "SQUAT" to 2,
        "LUNGE" to 3,
        "SHOULDER PRESS" to 4,
        "MOUNTAIN CLIMBING" to 5,
        "SIDE LATERAL RAISE" to 6,
        "SIDE LUNGE" to 7,
        "DUMBEL CURL" to 8
    )
>>>>>>> Stashed changes

    @SuppressLint("SetTextI18n")
    fun showDialog(dataList:ArrayList<testmodel>){

        val trainList = arrayListOf<testmodel>()
        val routineList = arrayListOf<ExerciseList>()
        trainList.addAll(dataList)

        dialog.setContentView(R.layout.fragment_store_train)
        dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            dialog.window?.setDecorFitsSystemWindows(false)
        }
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.findViewById<RecyclerView>(R.id.trainListRecycle)
            .layoutManager = LinearLayoutManager(context)
        dialog.findViewById<RecyclerView>(R.id.trainListRecycle)
            .adapter = StoreRecyclerAdapter(trainList)
        dialog.show()

        val title = dialog.findViewById<EditText>(R.id.routineTitle)
        val yes = dialog.findViewById<Button>(R.id.storeOk)
        val no = dialog.findViewById<Button>(R.id.storeNo)

        for (i in trainList) {
            routineList.add(ExerciseList(exerciseId[i.titles]!!, i.reps, i.sets))
        }

        dialog.findViewById<TextView>(R.id.breakTimeSet).text = "$breakTime 초"
        yes.setOnClickListener {
            val routine = PostTrainRoutine(title.text.toString(), System.currentTimeMillis(),breakTime,routineList)
            Log.d("YMC","루틴: $routine")
            PostTrainRoutineCall(context).postTrainRoutineCall(routine)
            dialog.dismiss()
        }
        no.setOnClickListener {
            dialog.dismiss()
        }
    }
}