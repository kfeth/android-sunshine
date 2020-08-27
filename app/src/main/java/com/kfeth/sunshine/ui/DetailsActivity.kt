package com.kfeth.sunshine.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kfeth.sunshine.R
import com.kfeth.sunshine.data.WeatherLocation
import com.kfeth.sunshine.viewmodels.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        viewModel.location.observe(this, {
            Log.d("DetailsActivity", "location: $it")
        })

        viewModel.weather.observe(this, {
            Log.d("DetailsActivity", "weather: $it")
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