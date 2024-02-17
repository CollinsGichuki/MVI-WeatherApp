package com.example.mvi_weatherapp.domain.repostitory

import com.example.mvi_weatherapp.domain.util.Resource
import com.example.mvi_weatherapp.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, lng: Double): Resource<WeatherInfo>
}