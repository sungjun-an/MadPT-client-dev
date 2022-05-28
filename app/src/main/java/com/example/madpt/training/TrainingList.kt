package com.example.madpt.training

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.R
import com.example.madpt.testmodel
import java.util.*
import kotlin.collections.ArrayList

class TrainingList(private val dataList:ArrayList<testmodel>, listen:OnRemove, private val context: Context, listener: FixExercise):
    RecyclerView.Adapter<TrainingList.ViewHolder>(), Swaping {

    private var onRemoveClickListen = listen
    private val fixExercise = listener

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val trainImage = itemView.findViewById<ImageView>(R.id.list_image)
        val cancelTrain = itemView.findViewById(R.id.removeTrain) as ImageButton

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

    override fun onBindViewHolder(viewholder: ViewHolder, @SuppressLint("RecyclerView") i: Int) {
        viewholder.cancelTrain.setOnClickListener {
            onRemoveClickListen.OnRemoveClick(i)
            notifyDataSetChanged()
        }
        viewholder.itemView.setOnClickListener {
            val dialog = SetDialog(context, dataList[i].titles, dataList[i].images)
            dialog.showDialog(dataList[i].sets, dataList[i].reps)
            dialog.setOnClickListener(object : SetDialog.OnDialogClickListener {
                override fun onClicked(sets: Int, reps: Int, images: Int, itemTitles: String) {
                    fixExercise.fixEx(sets, reps, images, itemTitles, i)
                }
            })
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