package com.ekochkov.myweathers.data.entity

data class Point(
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    var weather: Weather? = null
)