package com.kfeth.sunshine.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.kfeth.sunshine.data.WeatherRepository

class DetailsViewModel @ViewModelInject constructor(
    private val repository: WeatherRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val resource = repository.getWeather(33.44, -94.04).asLiveData()
    val status = resource.map { it.status }
    val weather = resource.map { it.data }
}
