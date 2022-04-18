package com.example.madpt.training

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.R
import com.example.madpt.testmodel

class StoreTrain(context: Context, private val dataList:ArrayList<testmodel>, breakTime: Int): Store {

    private val context = context
    private val dialog = Dialog(context)
    private val trainList = dataList
    private val breakTime = breakTime

    @SuppressLint("SetTextI18n")
    fun showDialog(){

        dialog.setContentView(R.layout.fragment_store_train)
        dialog.window!!.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.findViewById<RecyclerView>(R.id.trainListRecycle)
            .layoutManager = LinearLayoutManager(context)
        dialog.findViewById<RecyclerView>(R.id.trainListRecycle)
            .adapter = StoreRecyclerAdapter( trainList)
        dialog.show()

        val title = dialog.findViewById<EditText>(R.id.routineTitle)
        val yes = dialog.findViewById<Button>(R.id.storeOk)
        val no = dialog.findViewById<Button>(R.id.storeNo)

        dialog.findViewById<TextView>(R.id.breakTimeSet).text = "$breakTime 초"
        yes.setOnClickListener {

        }
        no.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun store(trainList: ArrayList<testmodel>, breakTime: Int, title: String) {
        TODO("Not yet implemented")
    }
}