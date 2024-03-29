package com.kfeth.sunshine.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.kfeth.sunshine.data.WeatherRepository
import com.kfeth.sunshine.utils.isLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: WeatherRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val weatherId = savedStateHandle.getLiveData<Int>("weatherId")

    private val location = weatherId.switchMap {
        repository.getWeatherLocation(it).asLiveData()
    }

    private val resource = weatherId.switchMap {
        repository.getWeatherDetails(it).asLiveData()
    }

    fun onPullToRefresh() {
        val id = weatherId.value
        weatherId.value = id
    }

    fun toggleFavourite() {
        viewModelScope.launch {
            repository.toggleFavourite(weatherId.value ?: 0)
        }
    }

    val currentWeather = resource.map { it.data }
    val forecast = weatherId.switchMap { repository.getForecast(it).asLiveData() }
    val isFavourite = weatherId.switchMap { repository.isFavourite(it).asLiveData() }
    val title = location.map { it.name }

    val isLoading = resource.map { it.isLoading() }
    val errorMessage = resource.map { it.message }
}
