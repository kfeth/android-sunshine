package com.kfeth.sunshine.di

import com.kfeth.sunshine.SunshineApp
import javax.inject.Singleton

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityModule::class
])

interface AppComponent : AndroidInjector<SunshineApp> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<SunshineApp>()
}