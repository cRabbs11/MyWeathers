package com.ekochkov.myweathers.view.holder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ekochkov.myweathers.data.entity.WeatherHour
import com.ekochkov.myweathers.databinding.ItemWeatherHourBinding
import com.ekochkov.myweathers.utils.WeatherIconHelper
import java.text.SimpleDateFormat
import java.util.*

const val TIME_TODAY = "today"
const val TIME_TOMORROW = "tomorrow"

const val DATE_FORMAT_PATTERN_DAY = "dd"
const val DATE_FORMAT_PATTERN_MONTH = "MM"
const val DATE_FORMAT_PATTERN_DAY_MONTH = "dd.MM"
const val DATE_FORMAT_PATTERN_HOUR_MINUTE = "kk:mm"

class WeatherHourViewHolder(
    private val binding: ItemWeatherHourBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(weatherHour: WeatherHour) {
        binding.time.text = getTimeString(weatherHour.time.toLong()*1000)
        binding.temp.text = "${weatherHour.temp}°C"

        Glide
            .with(binding.root)
            .load(WeatherIconHelper.getIcon(weatherHour.iconId))
            .centerInside()
            .into(binding.icon);
    }

    private fun getTimeString(time: Long): String {
        val dateFormatCurrentDay = SimpleDateFormat(DATE_FORMAT_PATTERN_DAY, Locale.getDefault())
        val dateFormatCurrentMonth = SimpleDateFormat(DATE_FORMAT_PATTERN_MONTH, Locale.getDefault())
        val currentDay = Date()
        val stringCurrentDay = dateFormatCurrentDay.format(currentDay)
        val stringCurrentMonth = dateFormatCurrentMonth.format(currentDay)

        val dateFormatDay = SimpleDateFormat(DATE_FORMAT_PATTERN_DAY, Locale.getDefault())
        val dateFormatMonth = SimpleDateFormat(DATE_FORMAT_PATTERN_MONTH, Locale.getDefault())
        val dateDay = Date(time)
        var timeDay = dateFormatDay.format(dateDay)
        val timeMonth = dateFormatMonth.format(dateDay)

        //время прогноза "день"
        timeDay = if (stringCurrentDay==timeDay) {
            TIME_TODAY
        } else if ((stringCurrentDay.toInt()+1 == timeDay.toInt() && stringCurrentMonth==timeMonth) ||
            stringCurrentDay.toInt() > timeDay.toInt() && stringCurrentMonth.toInt()+1 == timeMonth.toInt()) {
            TIME_TOMORROW
        } else {
            val dateFormatDay = SimpleDateFormat(DATE_FORMAT_PATTERN_DAY_MONTH, Locale.getDefault())
            dateFormatDay.format(dateDay)
        }

        val dateFormatHour = SimpleDateFormat(DATE_FORMAT_PATTERN_HOUR_MINUTE, Locale.getDefault())
        val dateHour = Date(time)
        //время прогноза "час"
        val timeHour = dateFormatHour.format(dateHour)

        val time = "$timeDay\n$timeHour"
        return time
    }
}