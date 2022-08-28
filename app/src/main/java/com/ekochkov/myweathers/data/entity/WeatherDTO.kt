package com.ekochkov.myweathers.data.entity

data class WeatherDTO(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)