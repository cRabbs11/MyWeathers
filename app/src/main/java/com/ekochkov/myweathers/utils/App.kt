package com.ekochkov.myweathers.utils

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.ekochkov.myweathers.BuildConfig
import com.ekochkov.myweathers.di.AppComponent
import com.ekochkov.myweathers.di.DaggerAppComponent
import com.ekochkov.myweathers.di.modules.DataModule
import com.ekochkov.myweathers.di.modules.DomainModule
import com.ekochkov.myweathers.di.modules.RemoteModule
import com.ekochkov.myweathers.utils.NotificationConstants.CHANNEL_ID
import timber.log.Timber
import timber.log.Timber.Forest.plant


class App: Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }

        instance = this
        dagger = DaggerAppComponent.builder()
            .dataModule(DataModule())
            .remoteModule(RemoteModule())
            .domainModule(DomainModule(this))
            .build()

        createNotificationChannel()
    }

    companion object {
        lateinit var instance: App
            private set
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Задаем имя, описание и важность канала
            val name = "MyWeathersChannel"
            val descriptionText = "MyWeathers notification Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            //Создаем канал, передав в параметры его ID(строка), имя(строка), важность(константа)
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            //Отдельно задаем описание
            mChannel.description = descriptionText
            //Получаем доступ к менеджеру нотификаций
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            //Регистрируем канал
            notificationManager.createNotificationChannel(mChannel)
        }
    }
}