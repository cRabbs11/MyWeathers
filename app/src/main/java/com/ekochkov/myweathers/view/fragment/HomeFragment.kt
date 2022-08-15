package com.ekochkov.myweathers.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ekochkov.myweathers.databinding.FragmentHomeBinding
import com.ekochkov.myweathers.view.activity.MainActivity
import com.ekochkov.myweathers.viewModel.HomeFragmentViewModel

class HomeFragment: Fragment() {

    lateinit var binding : FragmentHomeBinding

    private val viewModel : HomeFragmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.weatherLiveData.observe(viewLifecycleOwner) {
            Log.d("BMTH","observed")
        }

        binding.fabAdd.setOnClickListener {
            (activity as MainActivity).openAddPointFragment()
        }
    }
}