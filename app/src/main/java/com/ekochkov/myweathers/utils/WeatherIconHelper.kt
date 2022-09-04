package com.ekochkov.myweathers.utils

import com.ekochkov.myweathers.R

object WeatherIconHelper {

    fun getIcon(icon: String?): Int {
        return when (icon) {
            API_Constants.ICON_SUN_DAY -> return R.drawable.icon_sun_day
            API_Constants.ICON_SUN_NIGHT -> return R.drawable.icon_sun_day
            API_Constants.ICON_CLOUDY_DAY -> return R.drawable.icon_cloudy
            API_Constants.ICON_CLOUDY_NIGHT -> return R.drawable.icon_cloudy
            API_Constants.ICON_CLOUD_DAY -> return R.drawable.icon_cloud
            API_Constants.ICON_CLOUD_NIGHT -> return R.drawable.icon_cloud
            API_Constants.ICON_OVERCAST_CLOUDY_DAY -> return R.drawable.icon_overcast_cloudy
            API_Constants.ICON_OVERCAST_CLOUDY_NIGHT -> return R.drawable.icon_overcast_cloudy
            API_Constants.ICON_LIGHT_RAIN_DAY -> return R.drawable.icon_light_rain_day
            API_Constants.ICON_LIGHT_RAIN_NIGHT -> return R.drawable.icon_light_rain_day
            else -> return 0
        }
    }
}