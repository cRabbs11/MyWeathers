package com.ekochkov.myweathers.viewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ekochkov.myweathers.data.entity.Point
import com.ekochkov.myweathers.domain.Interactor
import com.ekochkov.myweathers.utils.App
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddPointFragmentViewModel: ViewModel() {

    @Inject
    lateinit var interactor: Interactor

    val resultLiveData = MutableLiveData<Long>()

    init {
        App.instance.dagger.inject(this)
    }

    fun savePoint(point: Point) {
        GlobalScope.launch {
            val result = interactor.savePoint(point)
            resultLiveData.postValue(result)
        }
    }
}