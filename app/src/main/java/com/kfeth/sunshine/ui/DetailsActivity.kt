package com.kfeth.sunshine.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.PagerSnapHelper
import com.kfeth.sunshine.R
import com.kfeth.sunshine.adapters.CurrentWeatherAdapter
import com.kfeth.sunshine.databinding.ActivityDetailsBinding
import com.kfeth.sunshine.utilities.createBinding
import com.kfeth.sunshine.viewmodels.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val viewModel: DetailsViewModel by viewModels()
    private val listAdapter = CurrentWeatherAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = createBinding<ActivityDetailsBinding>(R.layout.activity_details).apply {
            lifecycleOwner = this@DetailsActivity
            viewModel = this@DetailsActivity.viewModel
        }

        binding.currentWeatherRecyclerView.apply {
            adapter = listAdapter
            addItemDecoration(PageIndicatorDecoration())
            PagerSnapHelper().attachToRecyclerView(this)
        }

        viewModel.location.observe(this, {
            Log.d("DetailsActivity", "location: $it")
        })

        viewModel.weather.observe(this, {
            listAdapter.replace(it)
        })
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