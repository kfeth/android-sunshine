package com.kfeth.sunshine.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kfeth.sunshine.data.WeatherRepository

class FavouritesViewModel @ViewModelInject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _clickSearchEvent = MutableLiveData(false)

    val clickSearchEvent: MutableLiveData<Boolean>
        get() = _clickSearchEvent

    fun onClickSearch() {
        _clickSearchEvent.value = true
    }

    fun onClickSearchComplete() {
        _clickSearchEvent.value = false
    }
}
