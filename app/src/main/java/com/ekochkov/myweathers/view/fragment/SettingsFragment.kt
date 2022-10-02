package com.ekochkov.myweathers.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ekochkov.myweathers.databinding.FragmentSettingsBinding
import com.ekochkov.myweathers.viewModel.SettingsFragmentViewModel
import timber.log.Timber

class SettingsFragment: Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    val viewModel: SettingsFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.notificationSetting.switchBtn.setOnCheckedChangeListener { compoundButton, boolean ->
            viewModel.changeNotificationValue(boolean)
        }

        viewModel.notificationPointLiveData.observe(viewLifecycleOwner) {
            binding.notificationSetting.description.text = it
        }

        viewModel.notificationValueLiveData.observe(viewLifecycleOwner) {
            if (binding.notificationSetting.switchBtn.isChecked!=it) {
                binding.notificationSetting.switchBtn.isChecked = it
            }
        }
    }
}