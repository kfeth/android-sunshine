package com.kfeth.sunshine.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.kfeth.sunshine.data.WeatherRepository
import com.kfeth.sunshine.data.entity.WeatherLocation
import com.kfeth.sunshine.utils.Resource
import com.kfeth.sunshine.utils.isLoading
import com.kfeth.sunshine.utils.sanitise
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class SearchViewModel @ViewModelInject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val searchChannel = ConflatedBroadcastChannel<String>()
    private val emptyFlow = flow { emit(Resource.success<List<WeatherLocation>>(emptyList())) }

    private val resource = searchChannel.asFlow().flatMapLatest { query ->
        when {
            query.isNotEmpty() -> repository.searchWeatherLocations(query)
            else -> emptyFlow
        }
    }.asLiveData()

    fun setQuery(query: String) {
        val input = query.sanitise()
        if (searchChannel.valueOrNull != input) {
            searchChannel.offer(input)
        }
    }

    val resultsList = resource.map { it.data.orEmpty() }
    val isLoading = resource.map { it.isLoading() }
    val errorMessage = resource.map { it.message }
}
