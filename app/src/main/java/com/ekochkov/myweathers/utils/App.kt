package com.ekochkov.myweathers.utils

import android.app.Application
import com.ekochkov.myweathers.BuildConfig
import com.ekochkov.myweathers.di.AppComponent
import com.ekochkov.myweathers.di.DaggerAppComponent
import com.ekochkov.myweathers.di.modules.DataModule
import com.ekochkov.myweathers.di.modules.DomainModule
import com.ekochkov.myweathers.di.modules.RemoteModule
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
    }

    companion object {
        lateinit var instance: App
            private set
    }
}