package com.kfeth.sunshine.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kfeth.sunshine.data.WeatherRepository
import javax.inject.Inject

class WeatherDetailsViewModel
@Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    private val _locationId = MutableLiveData<Int>()

    val locationId: LiveData<Int>
        get() = _locationId
}
