package com.ekochkov.myweathers.view.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ekochkov.myweathers.R
import com.ekochkov.myweathers.data.entity.Point
import com.ekochkov.myweathers.receivers.NotificationReceiver
import com.ekochkov.myweathers.utils.Constants
import com.ekochkov.myweathers.utils.DateConverter
import com.ekochkov.myweathers.utils.NotificationConstants
import com.ekochkov.myweathers.view.activity.MainActivity

object NotificationHelper {

    const val TIME_SECOND_IN_MILLIS = 1000L
    const val TIME_MINUTE_IN_MILLIS = TIME_SECOND_IN_MILLIS * 60L
    const val TIME_HOUR_IN_MILLIS = TIME_MINUTE_IN_MILLIS * 60L
    const val TIME_DAY_IN_MILLIS = TIME_HOUR_IN_MILLIS * 24L
    const val TIME_NOTIFICATION_HOUR_MINUTE = "08.00"

    fun createDelayNotification(context : Context, point: Point, delayInMillis: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(point.name, null, context, NotificationReceiver()::class.java)
        val bundle = Bundle()
        bundle.putParcelable(Constants.BUNDLE_KEY_POINT, point)
        intent.putExtra(Constants.BUNDLE_KEY, bundle)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(
            AlarmManager.RTC,
            getDelayNotificationTime(),
            pendingIntent
        )
    }

    fun cancelDelayNotification(context : Context, point: Point) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(point.name, null, context, NotificationReceiver()::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(pendingIntent)
    }

    fun createNotification(context: Context, point: Point) {
        val builder = NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_baseline_cloud_queue_24)
            setContentTitle(point.name)
            val contentText = "${point.weather?.temp}°C ${point.weather?.description}"?: context.getString(R.string.weather_not_found)
            setContentText(contentText)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(getPendingIntent(context, point))
            setAutoCancel(true)
        }

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(point.id, builder.build())
    }

    private fun getDelayNotificationTime(): Long {
        val currentDate = DateConverter.fromLongToText(System.currentTimeMillis(), DateConverter.FORMAT_DD_MM_YYYY)
        val setDate = "$currentDate $TIME_NOTIFICATION_HOUR_MINUTE"
        var setDateLong = DateConverter.fromTextToLong(setDate, DateConverter.FORMAT_DD_MM_YYYY_hh_mm)

        if (setDateLong!!<System.currentTimeMillis()) {
            setDateLong += TIME_DAY_IN_MILLIS
        }
        return setDateLong!!
    }

    private fun getPendingIntent(context: Context, point: Point): PendingIntent {
        return PendingIntent.getActivity(
            context,
            0,
            getIntent(context, point),
            PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun getIntent(context: Context, point: Point) : Intent {
        val intent = Intent(context, MainActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(Constants.BUNDLE_KEY_POINT, point)
        intent.putExtra(Constants.BUNDLE_KEY, bundle)
        return intent
    }
}