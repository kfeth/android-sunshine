package com.kfeth.sunshine.viewmodels

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.kfeth.sunshine.data.WeatherRepository
import com.kfeth.sunshine.ui.DetailsActivity.Companion.EXTRA_KEY_WEATHER_ID
import com.kfeth.sunshine.utilities.isLoading
import kotlinx.coroutines.launch

class DetailsViewModel @ViewModelInject constructor(
    private val repository: WeatherRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val weatherId = savedStateHandle.getLiveData<Int>(EXTRA_KEY_WEATHER_ID)

    private val location = weatherId.switchMap {
        repository.getWeatherLocation(it).asLiveData()
    }

    private val resource = location.switchMap {
        repository.getWeatherDetails(it.id, it.latitude, it.longitude).asLiveData()
    }

    private val _forecast = weatherId.switchMap {
        repository.getForecast(it).asLiveData()
    }

    private val _isFavourite = weatherId.switchMap {
        repository.isFavourite(it).asLiveData()
    }

    val forecast
        get() = _forecast

    val isFavourite
        get() = _isFavourite

    val isLoading = resource.map { it.isLoading() }
    val currentWeather = resource.map { it.data }
    val title = location.map { it.name }

    fun onPullToRefresh() {
        val id = weatherId.value
        weatherId.value = id
    }

    fun toggleFavourite() {
        viewModelScope.launch {
            repository.toggleFavourite(weatherId.value ?: 0)
        }
    }
}
