package com.kfeth.sunshine.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kfeth.sunshine.BuildConfig
import com.kfeth.sunshine.api.WeatherService
import com.kfeth.sunshine.data.api.asWeatherUpdate
import com.kfeth.sunshine.data.db.WeatherDao
import com.kfeth.sunshine.data.entity.joinIdsToString
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import timber.log.Timber

@HiltWorker
class RefreshWeatherWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val WORK_NAME = "${BuildConfig.APPLICATION_ID}.RefreshWeatherWorker"
    }

    override suspend fun doWork(): Result {
        try {
            val favourites = weatherDao.getFavourites().first().joinIdsToString()
            if (favourites.isNotEmpty()) {
                val response = weatherService.weatherForIds(favourites).body()
                weatherDao.updateWeather(response?.asWeatherUpdate())
            }
        } catch (throwable: Throwable) {
            Timber.e(throwable)
            return Result.retry()
        }
        return Result.success()
    }
}
