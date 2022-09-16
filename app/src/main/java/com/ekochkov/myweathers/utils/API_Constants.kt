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
    const val OPEN_WEATHER_PARAMETER_UNITS_VALUE_METRIC = "metric"
}