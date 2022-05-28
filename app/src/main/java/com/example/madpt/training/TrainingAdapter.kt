package com.example.madpt.training

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.R

class TrainingAdapter(private val context: Context, listener: OnRecyclerClickListener) : RecyclerView.Adapter<TrainingAdapter.ViewHolder> (){

    private val titles = arrayOf(
        "PUSH UP",
        "SQUAT",
        "LUNGE",
        "DUMBBELL",
        "MOUNTAIN CLIMBING",
        "SIDE LATERAL RAISE",
        "SIDE LUNGE",
        "DUMBEL CURL"
    )
    private val images = intArrayOf(
        R.drawable.pushup,
        R.drawable.standing,
        R.drawable.lunge,
        R.drawable.dumbell,
        R.drawable.dumbell,
        R.drawable.dumbell,
        R.drawable.dumbell,
        R.drawable.dumbell
    )
    private var onClickListen = listener

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        var trainingImage: ImageView
        var itemTitle: TextView
        val btn_add = itemView.findViewById(R.id.btn_add) as ImageButton

        init {
            trainingImage = itemView.findViewById(R.id.trainingImage)
            itemTitle = itemView.findViewById(R.id.itemTitle)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.training_list, viewGroup, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") i: Int) {
       viewHolder.btn_add.setOnClickListener {
           val dialog = SetDialog(context, titles[i], images[i])
           dialog.showDialog(0, 0)
           dialog.setOnClickListener(object : SetDialog.OnDialogClickListener {
               override fun onClicked(sets: Int, reps: Int, images: Int, itemTitles: String) {
                   onClickListen.onClick(sets, reps, images, itemTitles)
               }
           })
       }
       viewHolder.itemTitle.text = titles[i]
       viewHolder.trainingImage.setImageResource(images[i])
    }
 }