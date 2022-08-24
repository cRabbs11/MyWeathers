package com.ekochkov.myweathers.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekochkov.myweathers.data.entity.Point
import com.ekochkov.myweathers.domain.Interactor
import com.ekochkov.myweathers.domain.ResponseCallback
import com.ekochkov.myweathers.utils.App
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeFragmentViewModel: ViewModel() {

    val pointsListLiveData = MutableLiveData<List<Point>>()

    val pointsListData: Flow<List<Point>>

    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        pointsListData = interactor.getPoints()
    }

    fun getCitiesNearbyLocation(lat: Double, lon: Double) {
        //interactor.getCities(object: ResponseCallback<List<Point>> {
        //    override fun onSuccess(pointList: List<Point>) {
        //        Log.d("BMTH","size = ${pointList.size}")
        //        getWeather(pointList)
        //    }
//
        //    override fun onFailure() {
        //    }
        //})
    }

    private fun getWeather(list: List<Point>) {
        interactor.getWeatherByPoints(list, object: ResponseCallback<List<Point>> {
            override fun onSuccess(item: List<Point>) {
                pointsListLiveData.postValue(item)
            }

            override fun onFailure() {
            }
        })
    }

}