package com.ekochkov.myweathers.di.modules

import android.content.Context
import com.ekochkov.myweathers.data.PreferenceProvider
import com.ekochkov.myweathers.data.dao.PointDao
import com.ekochkov.myweathers.domain.Interactor
import com.ekochkov.myweathers.utils.GeoDBRetrofitInterface
import com.ekochkov.myweathers.utils.OpenWeatherRetrofitInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule(val context: Context) {

    @Provides
    fun provideContext(): Context = context

    @Singleton
    @Provides
    fun providePreferences(context: Context) = PreferenceProvider(context)

    @Singleton
    @Provides
    fun provideInteractor(
        weatherRetrofit: OpenWeatherRetrofitInterface,
        geoDBRetrofit: GeoDBRetrofitInterface,
        pointDao: PointDao,
        preference: PreferenceProvider) = Interactor(weatherRetrofit, geoDBRetrofit, pointDao, preference)
}