package com.ekochkov.myweathers.di

import com.ekochkov.myweathers.di.modules.DataModule
import com.ekochkov.myweathers.di.modules.DomainModule
import com.ekochkov.myweathers.di.modules.RemoteModule
import com.ekochkov.myweathers.receivers.NotificationReceiver
import com.ekochkov.myweathers.viewModel.AddPointFragmentViewModel
import com.ekochkov.myweathers.viewModel.HomeFragmentViewModel
import com.ekochkov.myweathers.viewModel.PointPageFragmentViewModel
import com.ekochkov.myweathers.viewModel.SettingsFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component (modules = [
    RemoteModule::class,
    DataModule::class,
    DomainModule::class])
interface AppComponent {

    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
    fun inject(addPointFragmentViewModel: AddPointFragmentViewModel)
    fun inject(pointPageFragmentViewModel: PointPageFragmentViewModel)
    fun inject(settingsFragmentViewModel: SettingsFragmentViewModel)
    fun inject(notificationReceiver: NotificationReceiver)
}