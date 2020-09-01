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
import com.kfeth.sunshine.data.ForecastWeather
import com.kfeth.sunshine.data.WeatherLocation
import com.kfeth.sunshine.data.WeatherRepository
import com.kfeth.sunshine.ui.DetailsActivity.Companion.EXTRA_KEY_WEATHER_ID
import com.kfeth.sunshine.utilities.Resource

class DetailsViewModel @ViewModelInject constructor(
    private val repository: WeatherRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val weatherId = savedStateHandle.getLiveData<Int>(EXTRA_KEY_WEATHER_ID)

    private val location: LiveData<WeatherLocation> = weatherId.switchMap {
        repository.getWeatherLocation(it).asLiveData()
    }

    private val resource: LiveData<Resource<CurrentWeather>> = location.switchMap {
        repository.getWeatherDetails(it.id, it.latitude, it.longitude).asLiveData()
    }

    val currentWeather: LiveData<CurrentWeather?> = resource.map { it.data }

    val forecast: LiveData<List<ForecastWeather>> = weatherId.switchMap {
        repository.getForecast(it).asLiveData()
    }

}
