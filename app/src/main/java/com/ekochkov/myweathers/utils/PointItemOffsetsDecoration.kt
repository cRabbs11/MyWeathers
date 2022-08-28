package com.ekochkov.myweathers.utils

import android.content.Context
import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import com.ekochkov.myweathers.R

class PointItemOffsetsDecoration(private val context: Context): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        super.getItemOffsets(outRect, itemPosition, parent)
        val hSide = context.resources.getDimension(R.dimen.offset_15).toInt()
        val vSide = context.resources.getDimension(R.dimen.offset_7).toInt()
        outRect.set(hSide, vSide, hSide, vSide)
    }
}