package com.ekochkov.myweathers.di.modules

import com.ekochkov.myweathers.domain.Interactor
import com.ekochkov.myweathers.utils.GeoDBRetrofitInterface
import com.ekochkov.myweathers.utils.OpenWeatherRetrofitInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideInteractor(weatherRetrofit: OpenWeatherRetrofitInterface,
    geoDBRetrofit: GeoDBRetrofitInterface) = Interactor(weatherRetrofit, geoDBRetrofit)
}