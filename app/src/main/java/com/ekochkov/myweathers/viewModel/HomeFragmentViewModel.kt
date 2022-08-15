package com.ekochkov.myweathers.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekochkov.myweathers.domain.Interactor
import com.ekochkov.myweathers.utils.App
import javax.inject.Inject

class HomeFragmentViewModel: ViewModel() {

    val weatherLiveData = MutableLiveData<String>()

    @Inject
    lateinit var interactor: Interactor

    init {
        Log.d("BMTH","init")
        App.instance.dagger.inject(this)

        interactor.getWeatherByCoords("37.39", "-122.08")
    }
}