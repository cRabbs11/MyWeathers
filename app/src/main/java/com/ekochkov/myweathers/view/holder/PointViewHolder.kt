package com.ekochkov.myweathers.view.holder

import androidx.recyclerview.widget.RecyclerView
import com.ekochkov.myweathers.R
import com.ekochkov.myweathers.data.entity.Point
import com.ekochkov.myweathers.databinding.ItemPointBinding

class PointViewHolder(private val binding: ItemPointBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(point: Point) {
        binding.name.text = point.name
        binding.description.text = point.weather?.description
        binding.temp.text = "${point.weather?.temp}°C"
        binding.tempMax.text = "${point.weather?.temp_max}°C"
        binding.tempMin.text = "${point.weather?.temp_min}°C"
        val icon = getIcon(point.weather?.iconId)
        if (icon!=0) {
            binding.tempIcon.setImageResource(icon)
        }


    }

    private fun getIcon(icon: String?) : Int {
        return when (icon) {
            "01d" -> return R.drawable.icon_sun_day
            "01n" -> return R.drawable.icon_sun_day
            "02d" -> return R.drawable.icon_cloudy
            "02n" -> return R.drawable.icon_cloudy
            "03d" -> return R.drawable.icon_cloud
            "03n" -> return R.drawable.icon_cloud
            "04d" -> return R.drawable.icon_overcast_cloudy
            "04n" -> return R.drawable.icon_overcast_cloudy
            else -> return 0
        }
    }
}