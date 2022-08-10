package com.ekochkov.myweathers.utils

import android.app.Application
import com.ekochkov.myweathers.di.AppComponent
import com.ekochkov.myweathers.di.DaggerAppComponent
import com.ekochkov.myweathers.di.modules.DataModule
import com.ekochkov.myweathers.di.modules.DomainModule
import com.ekochkov.myweathers.di.modules.RemoteModule

class App: Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        dagger = DaggerAppComponent.builder()
            .dataModule(DataModule())
            .remoteModule(RemoteModule())
            .domainModule(DomainModule())
            .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}