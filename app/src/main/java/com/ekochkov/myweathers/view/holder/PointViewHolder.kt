package com.ekochkov.myweathers.view.holder

import androidx.recyclerview.widget.RecyclerView
import com.ekochkov.myweathers.R
import com.ekochkov.myweathers.data.entity.Point
import com.ekochkov.myweathers.databinding.ItemPointBinding
import com.ekochkov.myweathers.utils.API_Constants
import com.ekochkov.myweathers.utils.PointListAdapter

class PointViewHolder(
    private val binding: ItemPointBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(point: Point, listener: PointListAdapter.PointClickListener) {
        binding.name.text = point.name
        binding.description.text = point.weather?.description
        binding.temp.text = "${point.weather?.temp}°C"
        binding.tempMax.text = "${point.weather?.temp_max}°C"
        binding.tempMin.text = "${point.weather?.temp_min}°C"
        val icon = getIcon(point.weather?.iconId)
        if (icon != 0) {
            binding.tempIcon.setImageResource(icon)
        }
        binding.root.setOnClickListener {
            listener.onPointClick(point)
        }

    }

    private fun getIcon(icon: String?): Int {
        return when (icon) {
            API_Constants.ICON_SUN_DAY -> return R.drawable.icon_sun_day
            API_Constants.ICON_SUN_NIGHT -> return R.drawable.icon_sun_day
            API_Constants.ICON_CLOUDY_DAY -> return R.drawable.icon_cloudy
            API_Constants.ICON_CLOUDY_NIGHT -> return R.drawable.icon_cloudy
            API_Constants.ICON_CLOUD_DAY -> return R.drawable.icon_cloud
            API_Constants.ICON_CLOUD_NIGHT -> return R.drawable.icon_cloud
            API_Constants.ICON_OVERCAST_CLOUDY_DAY -> return R.drawable.icon_overcast_cloudy
            API_Constants.ICON_OVERCAST_CLOUDY_NIGHT -> return R.drawable.icon_overcast_cloudy
            API_Constants.ICON_LIGHT_RAIN_DAY -> return R.drawable.icon_light_rain_day
            API_Constants.ICON_LIGHT_RAIN_NIGHT -> return R.drawable.icon_light_rain_day
            else -> return 0
        }
    }
}