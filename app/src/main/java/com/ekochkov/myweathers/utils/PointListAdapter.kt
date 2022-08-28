package com.ekochkov.myweathers.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ekochkov.myweathers.data.entity.Point
import com.ekochkov.myweathers.databinding.ItemPointBinding
import com.ekochkov.myweathers.view.holder.PointViewHolder

class PointListAdapter() : RecyclerView.Adapter<PointViewHolder>() {

    var pointsList = arrayListOf<Point>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
        val binding = ItemPointBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PointViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        holder.bind(pointsList[position])
    }

    override fun getItemCount(): Int {
        return pointsList.size
    }
}