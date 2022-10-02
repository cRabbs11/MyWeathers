package com.ekochkov.myweathers.data

import android.content.Context

class PreferenceProvider(context: Context) {

    private val appContext = context.applicationContext
    private val sharedPref = appContext.getSharedPreferences(PREFERENCE_SETTINGS_FILENAME, Context.MODE_PRIVATE)

    init {
        setPrefOnFirstAppLaunch()
    }

    fun setNotificationValue(value: Boolean) {
        val editor = sharedPref.edit()
        val put = editor.putBoolean(KEY_WEATHER_NOTIFICATION, value)
        put.apply()
    }

    fun getNotificationValue(): Boolean {
        return sharedPref.getBoolean(KEY_WEATHER_NOTIFICATION, false)
    }

    fun setPointId(pointId: Long) {
        val editor = sharedPref.edit()
        val put = editor.putLong(KEY_POINT_NOTIFICATION, pointId)
        put.apply()
    }

    fun getPointId(): Long {
        return sharedPref.getLong(KEY_POINT_NOTIFICATION, DEFAULT_POINT_NOTIFICATION)
    }



    private fun setPrefOnFirstAppLaunch() {
        if (sharedPref.getBoolean(KEY_FIRST_LAUNCH, true)) {
            sharedPref.edit().putBoolean(KEY_FIRST_LAUNCH, false).apply()
            sharedPref.edit().putBoolean(KEY_WEATHER_NOTIFICATION, false).apply()
        }
    }

    companion object {
        private const val PREFERENCE_SETTINGS_FILENAME = "settings"
        private const val KEY_FIRST_LAUNCH = "first_launch"
        private const val KEY_WEATHER_NOTIFICATION = "notification"
        private const val KEY_POINT_NOTIFICATION = "point_notification"
        const val DEFAULT_POINT_NOTIFICATION = -1L
    }
}