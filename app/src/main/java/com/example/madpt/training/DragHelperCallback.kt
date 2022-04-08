package com.example.madpt.training

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView

class DragHelperCallback(private val trainingList: TrainingList) : ItemTouchHelper.Callback() {

    private var currentDx = 0f
    private var currentPosition: Int? = null
    private var previousPosition: Int? = null

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(LEFT or RIGHT, LEFT or RIGHT)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val fromPos: Int = viewHolder.adapterPosition
        val toPos: Int = target.adapterPosition
        trainingList.swapData(fromPos, toPos)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        currentDx = 0f                                      // 현재 x 위치 초기화
        previousPosition = viewHolder.adapterPosition       // 드래그 또는 스와이프 동작이 끝난 view의 position 기억하기
        getDefaultUIUtil().clearView(getView(viewHolder))
    }
    
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        currentDx = 0f                                      // 현재 x 위치 초기화
        previousPosition = viewHolder.adapterPosition       // 드래그 또는 스와이프 동작이 끝난 view의 position 기억하기
        getDefaultUIUtil().clearView(getView(viewHolder))
    }
}