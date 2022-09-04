package com.ekochkov.myweathers.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import com.ekochkov.myweathers.data.entity.Point
import com.ekochkov.myweathers.databinding.FragmentHomeBinding
import com.ekochkov.myweathers.diff.PointDiff
import com.ekochkov.myweathers.utils.PointItemOffsetsDecoration
import com.ekochkov.myweathers.utils.PointListAdapter
import com.ekochkov.myweathers.view.activity.MainActivity
import com.ekochkov.myweathers.viewModel.HomeFragmentViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class HomeFragment: Fragment() {

    lateinit var adapter: PointListAdapter
    lateinit var binding : FragmentHomeBinding
    private val viewModel : HomeFragmentViewModel by viewModels()

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

        adapter = PointListAdapter(object: PointListAdapter.PointClickListener {
            override fun onPointClick(point: Point) {
                (activity as MainActivity).openPointPageFragment(point)
            }
        })
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addItemDecoration(PointItemOffsetsDecoration(requireContext()))

        viewModel.pointsListLiveData.observe(viewLifecycleOwner) { cityList->
            updateRecyclerView(cityList)
        }

        binding.fabAdd.setOnClickListener {
            (activity as MainActivity).openAddPointFragment()
        }
    }

    private fun updateRecyclerView(lists: List<Point>) {
        val diff = PointDiff(adapter.pointsList, lists)
        val diffResult = DiffUtil.calculateDiff(diff)
        val scrollPosition = adapter.pointsList.size
        adapter.pointsList.clear()
        adapter.pointsList.addAll(lists)
        diffResult.dispatchUpdatesTo(adapter)

        if (scrollPosition<adapter.pointsList.size) {
            binding.recyclerView.scrollToPosition(scrollPosition)
        }
    }
}