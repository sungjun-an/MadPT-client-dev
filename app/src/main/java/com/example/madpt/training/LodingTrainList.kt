package com.example.madpt.training

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.R
import com.example.madpt.storeTraining

class LodingTrainList(context: Context, storeTrainList: ArrayList<storeTraining>, listen: OnItemClickListener): OnItemClickListener {
    private val context = context
    private val dialog = Dialog(context)
    private val loadTrainList = storeTrainList

    private val listen = listen

    @SuppressLint("SetTextI18n")
    fun showDialog() {

        dialog.setContentView(R.layout.load_training_list)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.findViewById<RecyclerView>(R.id.load_train_list)
            .layoutManager = LinearLayoutManager(context)
        dialog.findViewById<RecyclerView>(R.id.load_train_list)
            .adapter = LoadRecyclerAdapter(loadTrainList, this)
        dialog.show()
    }

    override fun onClick(loadItem: storeTraining) {
        Log.d("YMC","$loadItem")
        listen.onClick(loadItem)
        dialog.dismiss()
    }

}