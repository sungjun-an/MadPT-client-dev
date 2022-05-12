package com.example.madpt.social

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.FriendsData
import com.example.madpt.FriendsDataList
import com.example.madpt.R
import com.example.madpt.databinding.FriedsListBinding
import com.example.madpt.inputTestFriendsData
import com.example.madpt.training.LoadRecyclerAdapter
import com.example.madpt.training.TrainingAdapter
import kotlinx.coroutines.NonDisposableHandle.parent

class FriendsListAdapter(private val context: Context) :
    RecyclerView.Adapter<FriendsListAdapter.FriendViewHolder>() {


    class FriendViewHolder(val binding: FriedsListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FriendViewHolder {

        val v =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.frieds_list, viewGroup, false)
        return FriendViewHolder(FriedsListBinding.bind(v))
    }

    override fun getItemCount(): Int {
        return FriendsDataList.size
    }

    override fun onBindViewHolder(viewHolder: FriendViewHolder, position: Int) {
        val show = FriendsDataList[position]

        when (position) {
            0 -> {
                viewHolder.binding.medal.setImageResource(R.drawable.gold_medal)
                viewHolder.binding.rank.visibility = View.INVISIBLE
            }
            1 -> {
                viewHolder.binding.medal.setImageResource(R.drawable.silver_medal)
                viewHolder.binding.rank.visibility = View.INVISIBLE
            }
            2 -> {
                viewHolder.binding.medal.setImageResource(R.drawable.bronze_medal)
                viewHolder.binding.rank.visibility = View.INVISIBLE
            }
            else -> {
                viewHolder.binding.medal.visibility = View.INVISIBLE
                viewHolder.binding.rank.visibility = View.VISIBLE
            }

        }
        viewHolder.binding.rank.setText((position+1).toString())
        viewHolder.binding.friendName.text = show.name
        viewHolder.binding.kcalScore.text = show.useKcal.toString()

    }
}

