package com.ekochkov.myweathers.utils

import com.ekochkov.myweathers.data.entity.OpenWeatherCurrentDataDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherRetrofitInterface {

    @GET("/data/2.5/weather")
    fun getCurrentByCoord(
        @Query("appid") appId: String,
        @Query("lat") coordLat: String,
        @Query("lon") coordLon: String,
        @Query("units") units: String? = null,
        @Query("lang") lang: String? = null
    ) : Call<OpenWeatherCurrentDataDTO>


}