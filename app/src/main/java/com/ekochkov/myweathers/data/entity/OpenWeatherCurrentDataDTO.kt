package com.ekochkov.myweathers.data.entity

import com.ekochkov.myweathers.utils.Constants

data class OpenWeatherCurrentDataDTO (
    val base: String,
    val clouds: CloudsDTO,
    val cod: Int,
    val coord: CoordDTO,
    val dt: Int,
    val id: Int,
    val main: MainDTO,
    val name: String,
    val sys: SysDTO,
    val timezone: Int,
    val visibility: Int,
    val weather: List<WeatherDTO>,
    val wind: WindDTO
)

fun OpenWeatherCurrentDataDTO.toWeather(): Weather {
    return Weather(temp = main.temp.toInt(),
        temp_max = main.temp_max.toInt(),
        temp_min = main.temp_min.toInt(),
        description = weather[0].description,
        iconId = weather.first().icon,
        windDeg = wind.deg,
        windSpeed = wind.speed,
        humidity = main.humidity,
        pressure = (main.pressure.toDouble() * Constants.WEATHER_PRESSURE_PHA_TO_MM_KOEF).toInt())
}