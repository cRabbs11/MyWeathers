package com.ekochkov.myweathers.view.holder

import androidx.recyclerview.widget.RecyclerView
import com.ekochkov.myweathers.data.entity.Point
import com.ekochkov.myweathers.databinding.ItemPointBinding
import com.ekochkov.myweathers.utils.PointListAdapter
import com.ekochkov.myweathers.utils.WeatherIconHelper

class PointViewHolder(
    private val binding: ItemPointBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(point: Point, listener: PointListAdapter.PointClickListener) {
        binding.name.text = point.name
        binding.description.text = point.weather?.description
        binding.temp.text = "${point.weather?.temp}°C"
        binding.tempMax.text = "${point.weather?.temp_max}°C"
        binding.tempMin.text = "${point.weather?.temp_min}°C"
        val icon = WeatherIconHelper.getIcon(point.weather?.iconId)
        if (icon != 0) {
            binding.tempIcon.setImageResource(icon)
        }
        binding.root.setOnClickListener {
            listener.onPointClick(point)
        }

    }
}