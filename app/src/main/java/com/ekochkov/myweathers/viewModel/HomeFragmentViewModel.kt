package com.ekochkov.myweathers.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekochkov.myweathers.data.entity.Point
import com.ekochkov.myweathers.data.entity.toCityList
import com.ekochkov.myweathers.domain.Interactor
import com.ekochkov.myweathers.domain.ResponseCallback
import com.ekochkov.myweathers.utils.App
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class HomeFragmentViewModel: ViewModel() {

    val pointsListLiveData = MutableLiveData<List<Point>>()

    private lateinit var scope: CoroutineScope


    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        getAllPoints()
    }

    fun getCities() {
        interactor.getCities(object: ResponseCallback<List<Point>> {
            override fun onSuccess(pointList: List<Point>) {
                getWeather(pointList)
            }

            override fun onFailure() {
            }
        })
    }

    private fun getAllPoints() {
        scope = CoroutineScope(Dispatchers.IO).also { scope ->
            scope.launch {
                interactor.getPointsFlow().collect {
                    val citiesData = interactor.getCitiesPoints()
                    if (citiesData!=null) {
                        val list = it + citiesData.toCityList()
                        getWeather(list)
                    }
                }
            }
        }
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

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

}