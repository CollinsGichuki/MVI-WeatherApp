package com.example.mvi_weatherapp.di

import com.example.mvi_weatherapp.data.repository.WeatherRepositoryImpl
import com.example.mvi_weatherapp.domain.repostitory.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(weatherRepository: WeatherRepositoryImpl): WeatherRepository
}