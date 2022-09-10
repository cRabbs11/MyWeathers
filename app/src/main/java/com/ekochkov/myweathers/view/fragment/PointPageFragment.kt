package com.ekochkov.myweathers.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import com.ekochkov.myweathers.data.entity.WeatherHour
import com.ekochkov.myweathers.databinding.FragmentPointPageCoordinatorBinding
import com.ekochkov.myweathers.diff.WeatherHourDiff
import com.ekochkov.myweathers.utils.*
import com.ekochkov.myweathers.view.activity.MainActivity
import com.ekochkov.myweathers.viewModel.PointPageFragmentViewModel
import com.ekochkov.myweathers.viewModel.factory
import timber.log.Timber

class PointPageFragment: Fragment() {

    private lateinit var binding : FragmentPointPageCoordinatorBinding
    lateinit var adapter: WeatherHourListAdapter

    private val viewModel : PointPageFragmentViewModel by viewModels { factory(arguments?.getParcelable(Constants.BUNDLE_KEY_POINT)) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPointPageCoordinatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as MainActivity).supportActionBar?.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = WeatherHourListAdapter()
        binding.pointPage.recyclerView.adapter = adapter
        binding.pointPage.recyclerView.addItemDecoration(PointItemOffsetsDecoration(requireContext()))

        viewModel.pointLiveData.observe(viewLifecycleOwner) { point ->
            Timber.d("point = $point")
            binding.toolbar.title = point.name
            binding.image.setImageResource(WeatherIconHelper.getIcon(point.weather?.iconId))
            binding.pointPage.temp.text = "${point.weather?.temp}°C"
            binding.pointPage.tempMax.text = "${point.weather?.temp_max}°C"
            binding.pointPage.tempMin.text = "${point.weather?.temp_min}°C"
            binding.pointPage.description.text = point.weather?.description
            binding.pointPage.humidityText.text = "${point.weather?.humidity}%"
            binding.pointPage.pressureText.text = "${point.weather?.pressure} мм рт.ст."
            binding.pointPage.windText.text = "${point.weather?.windSpeed?.toInt()} м/с"

            val forecast = point.weatherHourList
            if (forecast!=null) {
                updateRecyclerView(forecast)
            }

            point.weatherHourList?.forEach {
                Timber.d("${it} \n")
            }
        }
    }

    private fun updateRecyclerView(lists: List<WeatherHour>) {
        val diff = WeatherHourDiff(adapter.weatherHourList, lists)
        val diffResult = DiffUtil.calculateDiff(diff)
        adapter.weatherHourList.clear()
        adapter.weatherHourList.addAll(lists)
        diffResult.dispatchUpdatesTo(adapter)
    }
}