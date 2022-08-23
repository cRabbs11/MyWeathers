package com.ekochkov.myweathers.data.entity

data class Weather(
    val temp: Int,
    val temp_max: Int,
    val temp_min: Int,
    val description: String,
    val iconId: String
) {
}