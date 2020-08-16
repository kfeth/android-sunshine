package com.kfeth.sunshine.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.kfeth.sunshine.R
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class WeatherDetailsActivity : AppCompatActivity() {

    private val viewModel: WeatherDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)
    }
}