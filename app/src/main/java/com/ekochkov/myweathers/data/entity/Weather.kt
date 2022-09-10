package com.ekochkov.myweathers.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weather(
    val temp: Int,
    val temp_max: Int,
    val temp_min: Int,
    val description: String,
    val iconId: String,
    val windDeg : Int,
    val windSpeed : Double,
    val humidity: Int,
    val pressure: Int
): Parcelable {
}