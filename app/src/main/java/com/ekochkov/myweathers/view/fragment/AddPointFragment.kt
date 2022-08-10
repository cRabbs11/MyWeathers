package com.ekochkov.myweathers.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ekochkov.myweathers.databinding.FragmentAddPointBinding

class AddPointFragment: Fragment() {

    private lateinit var binding: FragmentAddPointBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPointBinding.inflate(inflater, container, false)
        return binding.root
    }
}