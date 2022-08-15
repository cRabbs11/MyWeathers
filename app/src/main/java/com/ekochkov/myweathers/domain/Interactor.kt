package com.ekochkov.myweathers.domain

import android.util.Log
import com.ekochkov.myweathers.data.entity.OpenWeatherCurrentDataDTO
import com.ekochkov.myweathers.utils.OpenWeatherRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val weatherRetrofitInterface: OpenWeatherRetrofitInterface) {

    fun getWeatherByCoords(lat: String, lon: String) {
        weatherRetrofitInterface.getCurrentByCoord(lat, lon).enqueue(object:
            Callback<OpenWeatherCurrentDataDTO> {
            override fun onResponse(call: Call<OpenWeatherCurrentDataDTO>, response: Response<OpenWeatherCurrentDataDTO>) {
                Log.d("BMTH","onResponse")
                Log.d("BMTH",response.body().toString())
            }

            override fun onFailure(call: Call<OpenWeatherCurrentDataDTO>, t: Throwable) {
                Log.d("BMTH","onFailure")
                Log.d("BMTH", t.printStackTrace().toString())
            }

        })
    }
}