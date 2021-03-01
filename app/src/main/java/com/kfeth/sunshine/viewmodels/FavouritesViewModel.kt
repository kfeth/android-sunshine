package com.kfeth.sunshine.viewmodels

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.kfeth.sunshine.data.WeatherRepository
import com.kfeth.sunshine.utils.isLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {

    private val prefs = booleanPreferencesKey("user_on_board")

    private val refreshEvent = MutableLiveData<Boolean>()

    private val resource = refreshEvent.switchMap {
        repository.getWeatherForFavourites().asLiveData()
    }

    init {
        refreshEvent.value = true
    }

    fun onPullToRefresh() {
        refreshEvent.value = true
    }

    fun onTutorialDismissed() {
        viewModelScope.launch { dataStore.edit { it[prefs] = true } }
    }

    val favourites = resource.map { it.data }
    val isLoading = resource.map { it.isLoading() }
    val errorMessage = resource.map { it.message }
    val userOnBoarded = dataStore.data.map { it[prefs] ?: false }.asLiveData()
}
