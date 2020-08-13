package com.kfeth.sunshine

import com.kfeth.sunshine.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class SunshineApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<SunshineApp> {
        return DaggerAppComponent.builder().create(this)
    }
}