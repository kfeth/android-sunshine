package com.kfeth.sunshine.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.kfeth.sunshine.R
import com.kfeth.sunshine.adapters.CurrentWeatherAdapter
import com.kfeth.sunshine.adapters.ForecastAdapter
import com.kfeth.sunshine.databinding.FragmentDetailsBinding
import com.kfeth.sunshine.utilities.bind
import com.kfeth.sunshine.utilities.showSnackBar
import com.kfeth.sunshine.viewmodels.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.forecastRecyclerView
import kotlinx.android.synthetic.main.fragment_details.root
import kotlinx.android.synthetic.main.fragment_details.viewPager

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bind<FragmentDetailsBinding>(R.layout.fragment_details, container).apply {
            lifecycleOwner = this@DetailsFragment
            viewModel = this@DetailsFragment.viewModel
            forecastRecyclerView.adapter = ForecastAdapter()

            viewPager.adapter = CurrentWeatherAdapter()
            TabLayoutMediator(tabLayout, viewPager) { _, _ -> }.attach()
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.apply {
            currentWeather.observe(viewLifecycleOwner, {
                (viewPager.adapter as CurrentWeatherAdapter).replace(it)
            })

            forecast.observe(viewLifecycleOwner, {
                (forecastRecyclerView.adapter as ForecastAdapter).submitList(it)
            })

            errorMessage.observe(viewLifecycleOwner, { root.showSnackBar(it) })

            title.observe(viewLifecycleOwner, {
                (activity as AppCompatActivity).supportActionBar?.title = it
            })
        }
    }
}
