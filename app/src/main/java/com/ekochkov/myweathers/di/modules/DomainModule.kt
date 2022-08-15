package com.ekochkov.myweathers.di.modules

import com.ekochkov.myweathers.domain.Interactor
import com.ekochkov.myweathers.utils.OpenWeatherRetrofitInterface
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideInteractor(weatherRetrofit: OpenWeatherRetrofitInterface) = Interactor(weatherRetrofit)
}