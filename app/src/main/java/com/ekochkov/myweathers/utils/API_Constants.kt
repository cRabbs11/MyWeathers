package com.ekochkov.myweathers.utils

object API_Constants {
    const val OPEN_WEATHER_MAP_BASE_URL = "https://api.openweathermap.org"
    const val GEO_DB_BASE_URL = "https://wft-geo-db.p.rapidapi.com/v1/"
    const val GEO_DB_FREE_BASE_URL = "http://geodb-free-service.wirefreethought.com/v1/"

    const val GEO_DB_PARAMETER_LIMIT_10 = 10
    const val GEO_DB_PARAMETER_SORT_POPULATION = "population"
    const val GEO_DB_PARAMETER_TYPES_CITY = "CITY"
    const val GEO_DB_PARAMETER_COUNTRY_RU = "ru"
    const val GEO_DB_PARAMETER_POPULATION_1M = 1000000

    const val OPEN_WEATHER_PARAMETER_API_KEY_NAME = "appid"
    const val OPEN_WEATHER_PARAMETER_UNITS_VALUE = "metric"

    const val ICON_SUN_DAY = "01d"
    const val ICON_SUN_NIGHT = "01n"
    const val ICON_CLOUDY_DAY = "02d"
    const val ICON_CLOUDY_NIGHT = "02n"
    const val ICON_CLOUD_DAY = "03d"
    const val ICON_CLOUD_NIGHT = "03n"
    const val ICON_OVERCAST_CLOUDY_DAY = "04d"
    const val ICON_OVERCAST_CLOUDY_NIGHT = "04n"
    const val ICON_LIGHT_RAIN_DAY = "10d"
    const val ICON_LIGHT_RAIN_NIGHT = "10n"
}