package com.kfeth.sunshine.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.kfeth.sunshine.data.WeatherRepository

class SearchViewModel @ViewModelInject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    fun setSearchQuery(search: String) {}

    val weatherList = repository.weatherList.asLiveData()

}
