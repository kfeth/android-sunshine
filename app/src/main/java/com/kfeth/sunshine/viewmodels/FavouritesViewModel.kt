package com.kfeth.sunshine.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.kfeth.sunshine.data.WeatherRepository
import com.kfeth.sunshine.utilities.isLoading

class FavouritesViewModel @ViewModelInject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val refreshEvent = MutableLiveData<Boolean>()

    private val resource = refreshEvent.switchMap {
        repository.getWeatherForFavourites().asLiveData()
    }

    init {
        refreshEvent.value = true
    }

    val favourites = resource.map { it.data }
    val isLoading = resource.map { it.isLoading() }
    val errorMessage = resource.map { it.message }

    fun onPullToRefresh() {
        refreshEvent.value = true
    }
}
