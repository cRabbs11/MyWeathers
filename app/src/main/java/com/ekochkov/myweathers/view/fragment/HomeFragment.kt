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
    private lateinit var scope: CoroutineScope

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    private val permissionLocationLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
                if (location!=null) {
                    viewModel.getCitiesNearbyLocation(location.latitude, location.longitude)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
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

        adapter = PointListAdapter()
        binding.recyclerView.adapter = adapter

        binding.recyclerView.addItemDecoration(PointItemOffsetsDecoration(requireContext()))

        viewModel.pointsListLiveData.observe(viewLifecycleOwner) { cityList->
            cityList.forEach {
                Log.d("BMTH", it.toString())
            }
            updateRecyclerView(cityList)
        }

        scope = CoroutineScope(Dispatchers.IO).also { scope ->
            scope.launch {
                viewModel.pointsListData.collect {
                    withContext(Dispatchers.Main) {
                        updateRecyclerView(it)
                    }
                }
            }
        }

        binding.fabAdd.setOnClickListener {
            (activity as MainActivity).openAddPointFragment()
        }

        if (checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
                if (location!=null) {
                    viewModel.getCitiesNearbyLocation(location.latitude, location.longitude)
                }
            }
        } else {
            requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    override fun onStop() {
        super.onStop()
        scope.cancel()
    }

    private fun requestPermission(permission: String) {
        when (permission) {
            Manifest.permission.ACCESS_COARSE_LOCATION -> {
                permissionLocationLauncher.launch(permission)
            }
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission) == PackageManager.PERMISSION_GRANTED
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