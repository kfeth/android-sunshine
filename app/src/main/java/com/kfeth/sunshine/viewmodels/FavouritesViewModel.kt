package com.kfeth.sunshine.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.kfeth.sunshine.data.FavouriteItem
import com.kfeth.sunshine.data.WeatherRepository
import com.kfeth.sunshine.utilities.Resource
import com.kfeth.sunshine.utilities.isLoading
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavouritesViewModel @ViewModelInject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private var resource = MutableLiveData<Resource<List<FavouriteItem>>>()

    init {
        refreshContent()
    }

    private fun refreshContent() {
        viewModelScope.launch {
            repository.getWeatherForFavourites().collect {
                resource.value = it
            }
        }
    }

    val favourites = resource.map { it.data }
    val isLoading = resource.map { it.isLoading() }

    fun onPullToRefresh() {
        refreshContent()
    }
}
