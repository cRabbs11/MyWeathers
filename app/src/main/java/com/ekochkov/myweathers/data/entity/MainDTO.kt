package com.ekochkov.myweathers.data.entity

data class MainDTO(
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double,
    val grnd_level: Int?,
    val sea_level: Int?,
    val temp_kf: Double?,

)