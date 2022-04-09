package com.example.madpt.training

interface OnRecyclerClickListener {
    fun onClick(set: Int, rep: Int, image: Int, itemTitle: String)
}

interface OnRemove{
    fun OnRemoveClick(position: Int)
}

interface SetBreakTime{
    fun SetBreak(time: Int)
}