package com.kfeth.sunshine.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.kfeth.sunshine.data.CurrentWeather
import com.kfeth.sunshine.data.WeatherLocation
import com.kfeth.sunshine.data.WeatherRepository
import com.kfeth.sunshine.ui.DetailsActivity.Companion.EXTRA_KEY_WEATHER_ID
import com.kfeth.sunshine.utilities.Resource

class DetailsViewModel @ViewModelInject constructor(
    private val repository: WeatherRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val weatherId = savedStateHandle.getLiveData<Int>(EXTRA_KEY_WEATHER_ID)

    val location: LiveData<WeatherLocation> = weatherId.switchMap {
        repository.getWeatherLocation(it).asLiveData()
    }

    private val resource: LiveData<Resource<CurrentWeather>> = weatherId.switchMap {
        repository.getCurrentWeather(it).asLiveData()
    }

    val weather: LiveData<CurrentWeather?> = resource.map { it.data }

}
