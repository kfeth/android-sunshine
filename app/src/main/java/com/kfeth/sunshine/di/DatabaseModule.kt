package com.kfeth.sunshine.di

import android.content.Context
import androidx.room.Room
import com.kfeth.sunshine.data.db.AppDatabase
import com.kfeth.sunshine.data.db.LocationDao
import com.kfeth.sunshine.data.db.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "sunshine-db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWeatherDao(database: AppDatabase): WeatherDao {
        return database.weatherDao()
    }

    @Provides
    @Singleton
    fun provideLocationDao(database: AppDatabase): LocationDao {
        return database.locationDao()
    }
}
