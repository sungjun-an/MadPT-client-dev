package com.example.madpt.training

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.R
import com.example.madpt.testmodel
import java.util.*
import kotlin.collections.ArrayList

class TrainingList(private val dataList:ArrayList<testmodel>, listen:OnRemove):
    RecyclerView.Adapter<TrainingList.ViewHolder>(), Swaping {

    private var onRemoveClickListen = listen



    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val trainImage = itemView.findViewById<ImageView>(R.id.list_image)
        val cancelTrain = itemView.findViewById(R.id.removeTrain) as Button

        fun bind(data: testmodel){
            trainImage.setImageResource(data.images)
        }
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.selected_train_list, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewholder: ViewHolder, i: Int) {
        viewholder.cancelTrain.setOnClickListener {
            onRemoveClickListen.OnRemoveClick(i)
            notifyDataSetChanged()
        }
        viewholder.bind(dataList[i])
    }
    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun swapData(fromPos: Int, toPos: Int) {
        Collections.swap(dataList,fromPos, toPos)

        notifyItemMoved(fromPos, toPos)
    }
}