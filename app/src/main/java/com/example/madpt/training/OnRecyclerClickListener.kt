package com.example.madpt.training

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

interface Store{
    fun store(trainList:ArrayList<testmodel>, breakTime:Int, title:String)
}