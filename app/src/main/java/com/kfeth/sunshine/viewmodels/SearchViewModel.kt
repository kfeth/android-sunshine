package com.kfeth.sunshine.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.kfeth.sunshine.data.WeatherRepository

class SearchViewModel @ViewModelInject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

}
