package com.example.madpt.social

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madpt.API.social.Friends
import com.example.madpt.API.social.FriendsDataList
import com.example.madpt.FriendsData
import com.example.madpt.R
import com.example.madpt.databinding.FriendsListWithMedalBinding
import com.example.madpt.databinding.FriendsListWithoutMedalBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class FriendsListAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class FriendsListWithMedalViewHolder(private val FriendsListwithMedal: FriendsListWithMedalBinding) :
        RecyclerView.ViewHolder(FriendsListwithMedal.root) {
        fun bind(friends: Friends) {
            FriendsListwithMedal.friendName.text = friends.name
            FriendsListwithMedal.kcalScore.text = friends.somo_kcal.toString()
            when (adapterPosition) {
                0 -> FriendsListwithMedal.medal.setImageResource(R.drawable.gold_medal)
                1 -> FriendsListwithMedal.medal.setImageResource(R.drawable.silver_medal)
                2 -> FriendsListwithMedal.medal.setImageResource(R.drawable.bronze_medal)
            }
        }
    }

    inner class FriendsListWithoutMedalViewHolder(private val FriendsListwithoutMedal: FriendsListWithoutMedalBinding) :
        RecyclerView.ViewHolder(FriendsListwithoutMedal.root) {
        fun bind(friends: Friends) {
            FriendsListwithoutMedal.friendName.text = friends.name
            FriendsListwithoutMedal.kcalScore.text = friends.somo_kcal.toString()
            FriendsListwithoutMedal.rank.text = adapterPosition.toString()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): RecyclerView.ViewHolder {

        return if (i == 1) {
            val view =
                FriendsListWithMedalBinding.inflate(LayoutInflater.from(context), viewGroup, false)
            FriendsListWithMedalViewHolder(view)
        } else {
            val view =
                FriendsListWithoutMedalBinding.inflate(
                    LayoutInflater.from(context),
                    viewGroup,
                    false
                )
            FriendsListWithoutMedalViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return FriendsDataList.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val show = FriendsDataList[position]
        if (position < 3) (viewHolder as FriendsListWithMedalViewHolder).bind(show)
        else (viewHolder as FriendsListWithoutMedalViewHolder).bind(show)

    }


    override fun getItemViewType(position: Int): Int {
        var isMedal: Int
        if (position < 3) isMedal = 1
        else isMedal = 0
        return isMedal
    }
}

