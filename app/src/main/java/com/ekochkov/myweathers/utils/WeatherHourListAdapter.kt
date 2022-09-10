package com.ekochkov.myweathers.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ekochkov.myweathers.data.entity.WeatherHour
import com.ekochkov.myweathers.databinding.ItemWeatherHourBinding
import com.ekochkov.myweathers.view.holder.WeatherHourViewHolder

class WeatherHourListAdapter() : RecyclerView.Adapter<WeatherHourViewHolder>() {

    var weatherHourList = arrayListOf<WeatherHour>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHourViewHolder {
        val binding = ItemWeatherHourBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherHourViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherHourViewHolder, position: Int) {
        holder.bind(weatherHourList[position])
    }

    override fun getItemCount(): Int {
        return weatherHourList.size
    }
}