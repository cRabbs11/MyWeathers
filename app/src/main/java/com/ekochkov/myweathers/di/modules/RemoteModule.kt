package com.ekochkov.myweathers.di.modules

import com.ekochkov.myweathers.utils.API_Constants
import com.ekochkov.myweathers.utils.GeoDBRetrofitInterface
import com.ekochkov.myweathers.utils.OpenWeatherRetrofitInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class RemoteModule {

    private val CALL_TIMEOUT_MILLI_30 = 30L

    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            callTimeout(CALL_TIMEOUT_MILLI_30, TimeUnit.SECONDS)
            readTimeout(CALL_TIMEOUT_MILLI_30, TimeUnit.SECONDS)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }).build()
        }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofitOpenWeather(okHttpClient: OkHttpClient): OpenWeatherRetrofitInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_Constants.OPEN_WEATHER_MAP_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(OpenWeatherRetrofitInterface::class.java)
        return retrofit
    }

    @Singleton
    @Provides
    fun provideRetrofitGeoDB(provideOkHttpClientGeoDB: OkHttpClient): GeoDBRetrofitInterface {
        val retrofit = Retrofit.Builder()
            .baseUrl(API_Constants.GEO_DB_FREE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClientGeoDB)
            .build().create(GeoDBRetrofitInterface::class.java)
        return retrofit
    }



}