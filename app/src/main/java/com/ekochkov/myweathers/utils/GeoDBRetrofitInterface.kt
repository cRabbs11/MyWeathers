package com.ekochkov.myweathers.utils

import com.ekochkov.myweathers.data.entity.GeoDBNearbyCitiesDataDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GeoDBRetrofitInterface {

    @GET("geo/locations/{locationId}/nearbyCities")
    fun getNearCities(
        @Path("locationId") locationId: String
    ) : Call<GeoDBNearbyCitiesDataDTO>

    @GET("geo/cities")
    fun getCities(
        @Query("limit") limit: Int,
        @Query("types") types: String,
        @Query("countryIds") countryIds: String,
        @Query("minPopulation") minPopulation: Int
    ) : Call<GeoDBNearbyCitiesDataDTO>
}