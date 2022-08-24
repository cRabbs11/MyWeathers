package com.ekochkov.myweathers.domain

import android.util.Log
import com.ekochkov.myweathers.data.dao.PointDao
import com.ekochkov.myweathers.data.entity.*
import com.ekochkov.myweathers.utils.API_Constants
import com.ekochkov.myweathers.utils.API_KEYS
import com.ekochkov.myweathers.utils.GeoDBRetrofitInterface
import com.ekochkov.myweathers.utils.OpenWeatherRetrofitInterface
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val weatherRetrofitInterface: OpenWeatherRetrofitInterface,
private val geoDBRetrofitInterface: GeoDBRetrofitInterface, private val pointDao: PointDao) {

    fun getPoints() : Flow<List<Point>> {
        return pointDao.getPoints()
    }

    fun savePoint(point: Point) {
        pointDao.insertPoint(point)
    }

    fun getCities(listener: ResponseCallback<List<Point>>) {
        geoDBRetrofitInterface.getCities(
            API_Constants.GEO_DB_PARAMETER_LIMIT_10,
            API_Constants.GEO_DB_PARAMETER_TYPES_CITY,
            API_Constants.GEO_DB_PARAMETER_COUNTRY_RU,
            API_Constants.GEO_DB_PARAMETER_POPULATION_1M).enqueue(object: Callback<GeoDBNearbyCitiesDataDTO> {
            override fun onResponse(call: Call<GeoDBNearbyCitiesDataDTO>, response: Response<GeoDBNearbyCitiesDataDTO>) {
                if (response.isSuccessful) {
                    val list = response.body()?.toCityList()
                    if (list!=null) {
                        listener.onSuccess(list)
                    } else {
                        listener.onFailure()
                    }
                } else {
                    listener.onFailure()
                }
            }

            override fun onFailure(call: Call<GeoDBNearbyCitiesDataDTO>, t: Throwable) {
                listener.onFailure()
            }
        })
    }

    fun getWeatherByPoints(list: List<Point>, listener: ResponseCallback<List<Point>>) {
        var size = 0
        list.forEach {
            getWeatherByCoords(it.latitude.toString(), it.longitude.toString(), object: ResponseCallback<Weather> {
                override fun onSuccess(item: Weather) {
                    it.weather = item
                    size++
                    if (size>=list.size) {
                        listener.onSuccess(list)
                    }
                }

                override fun onFailure() {
                    size++
                    if (size>=list.size) {
                        listener.onSuccess(list)
                    }
                }
            })
        }
    }

    private fun getWeatherByCoords(lat: String, lon: String, listener: ResponseCallback<Weather>) {
        weatherRetrofitInterface.getCurrentByCoord(API_KEYS.WEATHER_API_KEY, lat, lon, API_Constants.OPEN_WEATHER_PARAMETER_UNITS_VALUE).enqueue(object:
            Callback<OpenWeatherCurrentDataDTO> {
            override fun onResponse(call: Call<OpenWeatherCurrentDataDTO>, response: Response<OpenWeatherCurrentDataDTO>) {
                if (response.isSuccessful) {
                    val weather = response.body()?.toWeather()
                    if (weather!=null) {
                        listener.onSuccess(weather)
                    } else {
                        listener.onFailure()
                    }
                } else {
                    listener.onFailure()
                }
            }

            override fun onFailure(call: Call<OpenWeatherCurrentDataDTO>, t: Throwable) {
                Log.d("BMTH","onFailure")
                Log.d("BMTH", t.printStackTrace().toString())
            }
        })
    }

    private fun getCitiesNearbyLocation(lat: Double, lon: Double, listener: ResponseCallback<List<Point>>) {
        val latString = if (lat<0) {"-$lat"} else {"+$lat" }
        val lonString = if (lon<0) {"-$lon"} else {"+$lon" }
        val locationId = latString+lonString
        geoDBRetrofitInterface.getNearCities(locationId).enqueue(object: Callback<GeoDBNearbyCitiesDataDTO> {
            override fun onResponse(call: Call<GeoDBNearbyCitiesDataDTO>, response: Response<GeoDBNearbyCitiesDataDTO>) {
                Log.d("BMTH","onResponse")
                Log.d("BMTH",response.body().toString())
                if (response.isSuccessful) {
                    val list = response.body()?.toCityList()
                    if (list!=null) {
                        listener.onSuccess(list)
                    } else {
                        listener.onFailure()
                    }
                } else {
                    listener.onFailure()
                }
            }

            override fun onFailure(call: Call<GeoDBNearbyCitiesDataDTO>, t: Throwable) {
                listener.onFailure()
            }
        })
    }
}

interface ResponseCallback<T> {
    fun onSuccess(item: T)
    fun onFailure()
}