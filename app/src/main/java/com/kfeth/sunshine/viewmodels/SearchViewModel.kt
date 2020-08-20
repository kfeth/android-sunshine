package com.kfeth.sunshine.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.kfeth.sunshine.data.Location
import com.kfeth.sunshine.data.WeatherRepository
import com.kfeth.sunshine.utilities.Resource
import com.kfeth.sunshine.utilities.isLoading
import com.kfeth.sunshine.utilities.sanitise
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class SearchViewModel @ViewModelInject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val searchChannel = ConflatedBroadcastChannel<String>()
    private val emptyFlow = flow { emit(Resource.success<List<Location>>(emptyList())) }

    val resource = searchChannel.asFlow().flatMapLatest { query ->
        when {
            query.isNotEmpty() -> repository.searchLocations(query)
            else -> emptyFlow
        }
    }.asLiveData()

    val isLoading = resource.map { it.isLoading() }

    fun setQuery(query: String) {
        val input = query.sanitise()
        if (searchChannel.valueOrNull != input) {
            searchChannel.offer(input)
        }
    }
}
