package com.kfeth.sunshine.di

import dagger.Module

@Module(includes = [ViewModelModule::class])
internal class AppModule {

    /*
    @Singleton
    @Provides
    fun provideDatabase(app: SunshineApp): SunshineDatabase =
            Room.databaseBuilder(app.applicationContext, SunshineDatabase::class.java, SunshineDatabase.NAME).build()
     */
}