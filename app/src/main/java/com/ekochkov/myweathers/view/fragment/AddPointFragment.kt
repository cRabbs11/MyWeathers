package com.ekochkov.myweathers.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ekochkov.myweathers.R
import com.ekochkov.myweathers.data.entity.Point
import com.ekochkov.myweathers.databinding.FragmentAddPointBinding
import com.ekochkov.myweathers.viewModel.AddPointFragmentViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class AddPointFragment: Fragment() {

    private lateinit var googleMap: GoogleMap
    private lateinit var binding: FragmentAddPointBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel: AddPointFragmentViewModel by viewModels()

    @SuppressLint("MissingPermission")
    private val permissionLocationLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
                if (location!=null) {
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(LatLng(location.latitude, location.longitude)))
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
        binding = FragmentAddPointBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(binding.map.id) as SupportMapFragment
        mapFragment.getMapAsync {
            googleMap = it
            binding.add.isEnabled = true
            binding.fabAdd.isEnabled = true
        }

        viewModel.resultLiveData.observe(viewLifecycleOwner) {
            if (it>0) {
                Toast.makeText(requireContext(), getString(R.string.point_is_saved), Toast.LENGTH_SHORT).show()
            }
        }

        binding.add.setOnClickListener {
            val latLng = googleMap.cameraPosition.target
            val name = binding.pointNameEditText.editText?.text.toString()
            if (name.isNotEmpty()) {
                val point = Point(id = 0, latitude = latLng.latitude, longitude = latLng.longitude, name = name, createdByUser = true)
                viewModel.savePoint(point)
            } else {
                Toast.makeText(requireContext(), getString(R.string.enter_point_name), Toast.LENGTH_SHORT).show()
            }
        }


        binding.fabAdd.setOnClickListener {
            if (googleMap!=null) {
                if (checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    fusedLocationClient.lastLocation.addOnSuccessListener { location : Location? ->
                        if (location!=null) {
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 10f))
                        }
                    }
                } else {
                    requestPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                }
            } else {
                Toast.makeText(requireContext(), getString(R.string.map_is_loading), Toast.LENGTH_SHORT).show()
            }
        }
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
}