package com.ekochkov.myweathers.domain

import android.content.Context
import com.ekochkov.myweathers.data.PreferenceProvider
import com.ekochkov.myweathers.data.dao.PointDao
import com.ekochkov.myweathers.data.entity.*
import com.ekochkov.myweathers.utils.API_Constants
import com.ekochkov.myweathers.utils.API_KEYS
import com.ekochkov.myweathers.utils.GeoDBRetrofitInterface
import com.ekochkov.myweathers.utils.OpenWeatherRetrofitInterface
import com.ekochkov.myweathers.view.notification.NotificationHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Interactor(private val weatherRetrofitInterface: OpenWeatherRetrofitInterface,
                 private val geoDBRetrofitInterface: GeoDBRetrofitInterface,
                 private val pointDao: PointDao,
                 private val preference: PreferenceProvider,
                 private val context: Context) {

    suspend fun getCitiesPoints() = geoDBRetrofitInterface.getCitiesSuspend(
        API_Constants.GEO_DB_PARAMETER_LIMIT_10,
        API_Constants.GEO_DB_PARAMETER_TYPES_CITY,
        API_Constants.GEO_DB_PARAMETER_COUNTRY_RU,
        API_Constants.GEO_DB_PARAMETER_POPULATION_1M)

    suspend fun getPoint(id: Int): Point? {
        return pointDao.getPoint(id)
    }

    fun getPointFlow(id: Long): Flow<Point?> {
        return pointDao.getPointFlow(id)
    }

    fun getPointsFlow() : Flow<List<Point>> {
        return pointDao.getPointsFlow()
    }

    suspend fun savePoint(point: Point): Long {
        return suspendCoroutine { continuation ->
            Executors.newSingleThreadExecutor().execute {
                val result = pointDao.insertPoint(point)
                continuation.resume(result)
            }
        }
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

    fun getWeatherForPoint(point: Point, listener: ResponseCallback<Point>) {
        getWeatherByCoords(point.latitude.toString(), point.longitude.toString(), object: ResponseCallback<Weather> {
            override fun onSuccess(item: Weather) {
                point.weather = item
                listener.onSuccess(point)
            }

            override fun onFailure() {
                listener.onFailure()
            }
        })
    }

    private fun getWeatherByCoords(lat: String, lon: String, listener: ResponseCallback<Weather>) {
        weatherRetrofitInterface.getCurrentByCoord(API_KEYS.WEATHER_API_KEY, lat, lon, API_Constants.OPEN_WEATHER_PARAMETER_UNITS_VALUE_METRIC).enqueue(object:
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
                Timber.tag("BMTH").d("onFailure")
                Timber.tag("BMTH").d(t.printStackTrace().toString())
            }
        })
    }

    fun getForecastByCoords(lat: String, lon: String, listener: ResponseCallback<List<WeatherHour>>) {
        Timber.d("getForecastByCoords ...")
        weatherRetrofitInterface.getForecastByCoord(API_KEYS.WEATHER_API_KEY, lat, lon, API_Constants.OPEN_WEATHER_PARAMETER_UNITS_VALUE_METRIC).enqueue(object:
            Callback<OpenWeatherForecastDataDTO> {
            override fun onResponse(
                call: Call<OpenWeatherForecastDataDTO>,
                response: Response<OpenWeatherForecastDataDTO>
            ) {
                if(response.isSuccessful) {
                    val list = response.body()?.toWeatherHourList()
                    if (list != null) {
                        listener.onSuccess(list)
                    } else {
                        listener.onFailure()
                    }
                } else {
                    listener.onFailure()
                }
            }

            override fun onFailure(call: Call<OpenWeatherForecastDataDTO>, t: Throwable) {
                listener.onFailure()
            }
        })
    }

    private fun getCitiesNearbyLocation(lat: Double, lon: Double, listener: ResponseCallback<List<Point>>) {
        val latString = if (lat < 0) "-$lat" else "+$lat"
        val lonString = if (lon < 0) "-$lon" else "+$lon"
        val locationId = latString+lonString
        geoDBRetrofitInterface.getNearCities(locationId).enqueue(object: Callback<GeoDBNearbyCitiesDataDTO> {
            override fun onResponse(call: Call<GeoDBNearbyCitiesDataDTO>, response: Response<GeoDBNearbyCitiesDataDTO>) {
                Timber.tag("BMTH").d("onResponse")
                Timber.tag("BMTH").d(response.body().toString())
                if (response.isSuccessful) {
                    val list = response.body()?.toCityList()
                    if (list != null) {
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

    fun getNotificationValue(): Boolean {
        return preference.getNotificationValue()
    }

    fun getPreferencePointId(): Long {
        return preference.getNotificationPointId()
    }

    fun setPreferencePointId(id: Long) {
        preference.setNotificationValue(true)
        preference.setNotificationPointId(id)
    }

    fun createDelayNotification(point: Point, delayInMillis: Long) {
        NotificationHelper.createDelayNotification(context, point, delayInMillis)
    }

    fun setNotificationOff() {
        preference.setNotificationValue(false)
        GlobalScope.launch {
            val pointId = preference.getNotificationPointId()
            if (pointId!=PreferenceProvider.DEFAULT_POINT_NOTIFICATION) {
                val point = getPoint(pointId.toInt())
                point?.let {
                    NotificationHelper.cancelDelayNotification(context, point)
                }
            }
        }
    }

    fun setNotificationOn(point: Point) {
        preference.setNotificationValue(true)
        GlobalScope.launch {
            val oldPointId = preference.getNotificationPointId()
            if (oldPointId!=PreferenceProvider.DEFAULT_POINT_NOTIFICATION && oldPointId.toInt()!=point.id) {
                val oldPoint = getPoint(oldPointId.toInt())
                oldPoint?.let {
                    NotificationHelper.cancelDelayNotification(context, oldPoint)
                }
            }
            val id = savePoint(point)
            setPreferencePointId(id)
            createDelayNotification(point, 10000)
        }
    }

    fun setNotificationOn() {
        preference.setNotificationValue(true)
        GlobalScope.launch {
            val pointId = preference.getNotificationPointId()
            val point = getPoint(pointId.toInt())
            point?.let {
                createDelayNotification(point, 10000)
            }
        }
    }
}

interface ResponseCallback<T> {
    fun onSuccess(item: T)
    fun onFailure()
}