package com.ekochkov.myweathers.view.holder

import androidx.recyclerview.widget.RecyclerView
import com.ekochkov.myweathers.data.entity.Point
import com.ekochkov.myweathers.databinding.ItemPointBinding

class PointViewHolder(private val binding: ItemPointBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(point: Point) {
        binding.name.text = point.name
        binding.description.text = point.weather?.description
        binding.temp.text = point.weather?.temp.toString()
        binding.tempMax.text = point.weather?.temp_max.toString()
        binding.tempMin.text = point.weather?.temp_min.toString()
    }
}