package com.ekochkov.myweathers.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekochkov.myweathers.data.entity.Point
import com.ekochkov.myweathers.data.entity.WeatherHour
import com.ekochkov.myweathers.data.entity.toCityList
import com.ekochkov.myweathers.domain.Interactor
import com.ekochkov.myweathers.domain.ResponseCallback
import com.ekochkov.myweathers.utils.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class PointPageFragmentViewModel(point: Point): ViewModel() {

    val pointLiveData = MutableLiveData<Point>()

    private lateinit var scope: CoroutineScope

    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        pointLiveData.postValue(point)
        getForecast(point)
    }

    private fun getForecast(point: Point) {
        scope = CoroutineScope(Dispatchers.IO).also { scope ->
            scope.launch {
                interactor.getForecastByCoords(point.latitude.toString(), point.longitude.toString(), object: ResponseCallback<List<WeatherHour>> {
                    override fun onSuccess(item: List<WeatherHour>) {
                        point.weatherHourList = item
                        pointLiveData.postValue(point)
                    }
                    override fun onFailure() {
                    }
                })
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}