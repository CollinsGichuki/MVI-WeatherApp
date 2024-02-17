package com.example.mvi_weatherapp.presentation

import com.example.mvi_weatherapp.domain.weather.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val isError: String? = null
)