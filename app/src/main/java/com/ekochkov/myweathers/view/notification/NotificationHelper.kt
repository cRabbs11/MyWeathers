package com.ekochkov.myweathers.view.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ekochkov.myweathers.R
import com.ekochkov.myweathers.data.entity.Point
import com.ekochkov.myweathers.utils.Constants
import com.ekochkov.myweathers.utils.Constants.WEATHER_NOT_FOUND
import com.ekochkov.myweathers.utils.NotificationConstants
import com.ekochkov.myweathers.view.activity.MainActivity

object NotificationHelper {

    fun createNotification(context: Context, point: Point) {
        val builder = NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_baseline_cloud_queue_24)
            setContentTitle(point.name)
            val contentText = "${point.weather?.temp}°C ${point.weather?.description}"?: WEATHER_NOT_FOUND
            setContentText(contentText)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(getPendingIntent(context, point))
            setAutoCancel(true)
        }

        val notificationManager = NotificationManagerCompat.from(context)

        //Отправляем изначальную нотификацю в стандартном исполнении
        notificationManager.notify(point.id, builder.build())
    }

    fun getPendingIntent(context: Context, point: Point): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(Constants.BUNDLE_KEY_POINT, point)
        intent.putExtra(Constants.BUNDLE_KEY, bundle)

        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    fun createNotificationChannel() {

    }
}