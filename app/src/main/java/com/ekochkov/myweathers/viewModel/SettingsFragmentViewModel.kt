package com.ekochkov.myweathers.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekochkov.myweathers.domain.Interactor
import com.ekochkov.myweathers.utils.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SettingsFragmentViewModel: ViewModel() {

    val notificationValueLiveData = MutableLiveData<Boolean>()
    val notificationPointLiveData = MutableLiveData<String>()

    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)

        notificationValueLiveData.postValue(interactor.getNotificationValue())
        updateNotificationPointLiveData()
    }

    private fun updateNotificationPointLiveData() {
        val pointId = interactor.getPreferencePointId()
        CoroutineScope(Dispatchers.IO).launch {
            interactor.getPointFlow(pointId).collect {
                it?.let {
                    notificationPointLiveData.postValue(it.name)
                }
            }
        }
    }

}