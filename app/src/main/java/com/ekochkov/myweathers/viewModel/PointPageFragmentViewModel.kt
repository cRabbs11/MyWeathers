package com.ekochkov.myweathers.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekochkov.myweathers.data.entity.Point
import com.ekochkov.myweathers.data.entity.WeatherHour
import com.ekochkov.myweathers.domain.Interactor
import com.ekochkov.myweathers.domain.ResponseCallback
import com.ekochkov.myweathers.utils.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class PointPageFragmentViewModel(private val point: Point): ViewModel() {

    val pointLiveData = MutableLiveData<Point>()
    val notificationLiveData = MutableLiveData<Boolean>()

    private lateinit var scope: CoroutineScope

    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        pointLiveData.postValue(point)
        getForecast(point)
        updateNotificationValue()
    }

    fun changeNotificationValue() {
        if (getNotificationValueForPoint()) {
            interactor.setNotificationOff()
        } else {
            interactor.setNotificationOn(point)
        }
        updateNotificationValue()
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

    private fun updateNotificationValue() {
        notificationLiveData.postValue(getNotificationValueForPoint())
    }

    private fun getNotificationValueForPoint(): Boolean {
        val notificationPointId = interactor.getPreferencePointId().toInt()
        val notificationIsOn = interactor.getNotificationValue()
        return notificationIsOn && notificationPointId==point.id
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
}