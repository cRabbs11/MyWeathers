package com.ekochkov.myweathers.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ekochkov.myweathers.data.entity.Point
import com.ekochkov.myweathers.domain.Interactor
import com.ekochkov.myweathers.domain.ResponseCallback
import com.ekochkov.myweathers.utils.App
import com.ekochkov.myweathers.utils.Constants
import com.ekochkov.myweathers.view.notification.NotificationHelper
import timber.log.Timber
import javax.inject.Inject

class NotificationReceiver: BroadcastReceiver() {

    @Inject
    lateinit var interactor: Interactor

    override fun onReceive(context: Context, intent: Intent?) {
        App.instance.dagger.inject(this)
        Timber.d("onReceive")
        val bundle = intent?.getBundleExtra(Constants.BUNDLE_KEY)
        val point = bundle?.getParcelable(Constants.BUNDLE_KEY_POINT) as Point?
        point?.let {
            interactor.getWeatherForPoint(it, object: ResponseCallback<Point> {
                override fun onSuccess(item: Point) {
                    NotificationHelper.createNotification(context, point)
                    NotificationHelper.createDelayNotification(context, point, NotificationHelper.TIME_MINUTE_IN_MILLIS)
                }

                override fun onFailure() {
                    NotificationHelper.createDelayNotification(context, point, NotificationHelper.TIME_DAY_IN_MILLIS)
                }
            })
        }
    }
}