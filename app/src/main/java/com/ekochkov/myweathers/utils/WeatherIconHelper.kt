package com.ekochkov.myweathers.utils

import com.ekochkov.myweathers.R

object WeatherIconHelper {

    fun getIcon(icon: String?): Int {
        return when (icon) {
            Constants.OPEN_WEATHER_ICON_SUN_DAY -> R.drawable.icon_sun_day
            Constants.OPEN_WEATHER_ICON_SUN_NIGHT -> R.drawable.icon_sun_day
            Constants.OPEN_WEATHER_ICON_CLOUDY_DAY -> R.drawable.icon_cloudy
            Constants.OPEN_WEATHER_ICON_CLOUDY_NIGHT -> R.drawable.icon_cloudy
            Constants.OPEN_WEATHER_ICON_CLOUD_DAY -> R.drawable.icon_cloud
            Constants.OPEN_WEATHER_ICON_CLOUD_NIGHT -> R.drawable.icon_cloud
            Constants.OPEN_WEATHER_ICON_OVERCAST_CLOUDY_DAY -> R.drawable.icon_overcast_cloudy
            Constants.OPEN_WEATHER_ICON_OVERCAST_CLOUDY_NIGHT -> R.drawable.icon_overcast_cloudy
            Constants.OPEN_WEATHER_ICON_LIGHT_RAIN_DAY -> R.drawable.icon_light_rain_day
            Constants.OPEN_WEATHER_ICON_LIGHT_RAIN_NIGHT -> R.drawable.icon_light_rain_day
            else -> 0
        }
    }
}