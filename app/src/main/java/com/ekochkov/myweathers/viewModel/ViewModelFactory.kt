package com.ekochkov.myweathers.viewModel

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ekochkov.myweathers.data.entity.Point
import com.ekochkov.myweathers.utils.Constants

class ViewModelFactory(private val point: Point?): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when (modelClass) {
            PointPageFragmentViewModel::class.java -> {
                if (point!=null) {
                    PointPageFragmentViewModel(point)
                } else {
                    throw IllegalStateException(Constants.EXCEPTION_MESSAGE_ARGUMENT_IS_NULL)
                }
            }
            else -> {
                throw IllegalStateException(Constants.EXCEPTION_MESSAGE_UNKNOWN_VIEW_MODEL)
            }
        }
        return viewModel as T
    }
}

fun Fragment.factory(point: Point? = null) = ViewModelFactory(point = point)