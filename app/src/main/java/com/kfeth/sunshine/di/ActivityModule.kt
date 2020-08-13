package com.kfeth.sunshine.di

import com.kfeth.sunshine.ui.WeatherDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeWeatherDetailsActivity(): WeatherDetailsActivity
}