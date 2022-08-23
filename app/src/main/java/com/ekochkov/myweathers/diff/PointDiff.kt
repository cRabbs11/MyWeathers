package com.ekochkov.myweathers.diff

import androidx.recyclerview.widget.DiffUtil
import com.ekochkov.myweathers.data.entity.Point

class PointDiff(val oldList: List<Point>, val newList: List<Point>): DiffUtil.Callback() {
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
        return old.name==new.name &&
                old.latitude==new.latitude &&
                old.longitude==new.longitude &&
                old.weather==new.weather
    }
}