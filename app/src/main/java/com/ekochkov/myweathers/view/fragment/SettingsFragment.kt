package com.ekochkov.myweathers.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ekochkov.myweathers.R
import com.ekochkov.myweathers.databinding.FragmentSettingsBinding
import com.ekochkov.myweathers.utils.DateConverter
import com.ekochkov.myweathers.view.activity.TAG_TIME_PICKER_FRAGMENT
import com.ekochkov.myweathers.view.notification.NotificationHelper
import com.ekochkov.myweathers.view.notification.NotificationHelper.TIME_DAY_IN_MILLIS
import com.ekochkov.myweathers.viewModel.SettingsFragmentViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import timber.log.Timber
import kotlin.getValue

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
            viewModel.changedNotificationValue(boolean)
        }

        viewModel.notificationPointLiveData.observe(viewLifecycleOwner) {
            binding.notificationSetting.description.text = it
        }

        viewModel.notificationValueLiveData.observe(viewLifecycleOwner) {
            if (binding.notificationSetting.switchBtn.isChecked!=it) {
                binding.notificationSetting.switchBtn.isChecked = it
            }
        }

        binding.notificationSetting.title.text = NotificationHelper.TIME_NOTIFICATION_HOUR_MINUTE

    }

    private fun openTimePicker() {
        val timePicker =
            MaterialTimePicker.Builder()
                .setTitleText(getString(R.string.notification_time))
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
        timePicker.show(childFragmentManager, TAG_TIME_PICKER_FRAGMENT)
        timePicker.addOnPositiveButtonClickListener {
            val hourInt = timePicker.hour
            val minuteInt = timePicker.minute

            val hour = if (hourInt <= 9) "0$hourInt" else "$hourInt"
            val minute = if (minuteInt <= 9) "0$minuteInt" else "$minuteInt"
            val setTime = "$hour.$minute"

            val currentDate = DateConverter.fromLongToText(System.currentTimeMillis(), DateConverter.FORMAT_DD_MM_YYYY)
            val setDate = "$currentDate $setTime"
            var setDateLong = DateConverter.fromTextToLong(setDate, DateConverter.FORMAT_DD_MM_YYYY_hh_mm)

            if (setDateLong!!<System.currentTimeMillis()) {
                setDateLong += TIME_DAY_IN_MILLIS
            }
            binding.notificationSetting.title.text = setTime
        }
    }


}