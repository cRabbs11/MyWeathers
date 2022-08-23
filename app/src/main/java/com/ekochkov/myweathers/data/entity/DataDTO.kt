package com.ekochkov.myweathers.data.entity

data class DataDTO(
    val country: String,
    val countryCode: String,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val region: String,
    val regionCode: Any,
    val type: String,
    val wikiDataId: String
)