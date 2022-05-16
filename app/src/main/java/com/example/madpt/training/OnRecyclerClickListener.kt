package com.example.madpt.training

import android.view.View
import com.example.madpt.API.routine.PostTrainRoutine
import com.example.madpt.storeTraining
import com.example.madpt.testmodel
import java.util.*

interface OnRecyclerClickListener {
    fun onClick(set: Int, rep: Int, image: Int, itemTitle: String)
}

interface OnRemove{
    fun OnRemoveClick(position: Int)
}

interface SetBreakTime{
    fun SetBreak(time: Int)
}

interface Swaping{
    fun swapData(fromPos: Int, toPos:Int)
}

interface StoreTraining{
    fun storeTrain(breakTime: Int, trainTitle: String, trainList:ArrayList<testmodel>)
}

interface OnItemClickListener {
    fun onClick(loadItem: PostTrainRoutine)
}