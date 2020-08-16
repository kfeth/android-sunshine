package com.kfeth.sunshine.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.kfeth.sunshine.data.WeatherRepository

class WeatherDetailsViewModel @ViewModelInject constructor(
    private val repository: WeatherRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

}
