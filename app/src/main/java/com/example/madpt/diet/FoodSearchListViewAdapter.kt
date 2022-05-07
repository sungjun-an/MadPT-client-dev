package com.example.madpt.diet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import com.example.madpt.API.food.food_list
import com.example.madpt.AddFoodData
import com.example.madpt.FoodData
import com.example.madpt.databinding.AddFoodListviewBinding
import com.example.madpt.databinding.SearchAddFoodListviewBinding

class FoodSearchListViewAdapter(
    private val context: Context,
    private val listViewAddFoodList: ArrayList<food_list>
) : BaseAdapter() {


    override fun getCount(): Int {
        return listViewAddFoodList.size
    }

    override fun getItem(position: Int): food_list = listViewAddFoodList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding = SearchAddFoodListviewBinding.inflate(LayoutInflater.from(context))

        val show = listViewAddFoodList[position]
        binding.makerName.text = show.maker_name
        binding.foodName.text = show.food_name
        binding.defaultKcal.text = show.food_data.defaultKcal.toInt().toString()
        return binding.root
    }

}