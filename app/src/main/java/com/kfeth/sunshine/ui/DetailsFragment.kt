package com.kfeth.sunshine.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.kfeth.sunshine.adapters.CurrentWeatherAdapter
import com.kfeth.sunshine.adapters.ForecastAdapter
import com.kfeth.sunshine.databinding.FragmentDetailsBinding
import com.kfeth.sunshine.utilities.showSnackBar
import com.kfeth.sunshine.viewmodels.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.root

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val currentWeatherAdapter = CurrentWeatherAdapter()
        val forecastAdapter = ForecastAdapter()

        binding.apply {
            lifecycleOwner = this@DetailsFragment
            viewModel = this@DetailsFragment.viewModel
            forecastList.adapter = forecastAdapter
            currentWeatherViewPager.adapter = currentWeatherAdapter
            TabLayoutMediator(tabLayout, currentWeatherViewPager) { _, _ -> }.attach()
        }

        subscribeUi(currentWeatherAdapter, forecastAdapter)
        return binding.root
    }

    private fun subscribeUi(
        currentWeatherAdapter: CurrentWeatherAdapter,
        forecastAdapter: ForecastAdapter
    ) {
        viewModel.apply {
            currentWeather.observe(viewLifecycleOwner) { currentWeatherAdapter.replace(it) }
            forecast.observe(viewLifecycleOwner) { forecastAdapter.submitList(it) }
            errorMessage.observe(viewLifecycleOwner) { root.showSnackBar(it) }
            title.observe(viewLifecycleOwner) {
                (activity as AppCompatActivity).supportActionBar?.title = it
            }
        }
    }
}
