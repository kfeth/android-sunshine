package com.kfeth.sunshine.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.PagerSnapHelper
import com.kfeth.sunshine.R
import com.kfeth.sunshine.adapters.CurrentWeatherAdapter
import com.kfeth.sunshine.adapters.ForecastAdapter
import com.kfeth.sunshine.databinding.ActivityDetailsBinding
import com.kfeth.sunshine.utilities.createBinding
import com.kfeth.sunshine.viewmodels.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val viewModel: DetailsViewModel by viewModels()
    private val currentWeatherAdapter = CurrentWeatherAdapter()
    private val forecastAdapter = ForecastAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createBinding<ActivityDetailsBinding>(R.layout.activity_details).apply {
            lifecycleOwner = this@DetailsActivity
            viewModel = this@DetailsActivity.viewModel
            forecastRecyclerView.apply { adapter = forecastAdapter }

            currentWeatherRecyclerView.apply {
                adapter = currentWeatherAdapter
                addItemDecoration(PageIndicatorDecoration())
                PagerSnapHelper().attachToRecyclerView(this)
            }
        }
        viewModel.currentWeather.observe(this, { currentWeatherAdapter.replace(it) })
        viewModel.forecast.observe(this, { forecastAdapter.submitList(it) })
    }

    companion object {
        const val EXTRA_KEY_WEATHER_ID = "keyWeatherId"
        fun newIntent(context: Context, weatherId: Int): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_KEY_WEATHER_ID, weatherId)
            return intent
        }
    }
}