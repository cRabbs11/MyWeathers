package com.ekochkov.myweathers.data.entity

data class ForecastDTO (
    val clouds: CloudsDTO,
    val dt: Int,
    val dt_txt: String,
    val main: MainDTO,
    val pop: Double,
    val rain: RainDTO,
    val sys: SysDTO,
    val visibility: Int,
    val weather: List<WeatherDTO>,
    val wind: WindDTO
)