package com.ekochkov.myweathers.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ekochkov.myweathers.data.entity.Point
import com.ekochkov.myweathers.databinding.FragmentPointPageBinding
import com.ekochkov.myweathers.utils.Constants
import timber.log.Timber

class PointPageFragment: Fragment() {

    private lateinit var binding : FragmentPointPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPointPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val point = bundle?.getParcelable<Point>(Constants.BUNDLE_KEY_POINT)
        Timber.d("point = $point")
        val temp = "+25\u00b0"
        binding.temp.text = temp
    }
}