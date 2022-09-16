package com.ekochkov.myweathers.diff

import androidx.recyclerview.widget.DiffUtil
import com.ekochkov.myweathers.data.entity.WeatherHour

class WeatherHourDiff(val oldList: List<WeatherHour>, val newList: List<WeatherHour>): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition]==newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        return old.time==new.time &&
                old.temp==new.temp &&
                old.iconId==new.iconId
    }
}