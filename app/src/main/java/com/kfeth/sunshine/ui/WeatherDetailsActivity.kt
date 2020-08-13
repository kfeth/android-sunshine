package com.kfeth.sunshine.ui

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kfeth.sunshine.R
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class WeatherDetailsActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(WeatherDetailsViewModel::class.java)
        viewModel.locationId.observe(this, Observer {
            Log.d("WeatherDetailsActivity", "Got $it")
        })
    }
}