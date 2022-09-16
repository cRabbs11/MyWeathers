package com.ekochkov.myweathers.data.entity

data class OpenWeatherForecastDataDTO(
    val city: CityDTO,
    val cnt: Int,
    val cod: String,
    val list: List<ForecastDTO>,
    val message: Int
)

fun OpenWeatherForecastDataDTO.toWeatherHourList(): List<WeatherHour> {
    val result = arrayListOf<WeatherHour>()
    list.forEach {
        result.add(
            WeatherHour(
                temp = it.main.temp.toInt(),
                description = it.weather.first().description,
                iconId = it.weather.first().icon,
                time = it.dt
            )
        )
    }
    return result
}