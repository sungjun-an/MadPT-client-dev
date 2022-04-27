package com.example.madpt.diet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.madpt.AddFoodData
import com.example.madpt.ModifyFoodData

import com.example.madpt.databinding.ActivityDietPageBinding
import com.example.madpt.databinding.AddFoodListviewBinding

class FoodListViewAdapter(
    private val context: Context,
    private val listViewAddFoodList: ArrayList<ModifyFoodData>
) : BaseAdapter() {

    override fun getCount(): Int {
        return listViewAddFoodList.size
    }

    override fun getItem(position: Int): ModifyFoodData = listViewAddFoodList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = AddFoodListviewBinding.inflate(LayoutInflater.from(context))

        val show = listViewAddFoodList[position]

        /*var tempMakerName : String = ""
        if(show.maker_name.equals("")){
            tempMakerName = show.maker_name!!
        }
        else{tempMakerName = "["+show.maker_name!!+"]"}*/

        binding.makerName.text = show.maker_name
        binding.foodName.text = show.food_name
        binding.defaultKcal.text = show.kcal.toString()

        return binding.root
    }

}