package com.kfeth.sunshine.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.kfeth.sunshine.data.FavouriteItem
import com.kfeth.sunshine.data.WeatherRepository
import com.kfeth.sunshine.utilities.Resource
import com.kfeth.sunshine.utilities.isLoading

class FavouritesViewModel @ViewModelInject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val refreshEvent = MutableLiveData(false)

    init {
        refreshEvent.value = true
    }

    private val resource = refreshEvent.switchMap {
        if (it) { repository.getWeatherForFavourites().asLiveData() }
        else { liveData { Resource.success<List<FavouriteItem>>(emptyList()) } }
    }

    val favourites = resource.map { it.data }
    val isLoading = resource.map { it.isLoading() }

    fun onPullToRefresh() {
        refreshEvent.value = true
    }
}
