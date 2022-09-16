package com.ekochkov.myweathers.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherHour (
    val temp: Int,
    val description: String,
    val iconId: String,
    val time: Int
): Parcelable