package com.example.mvi_weatherapp.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mvi_weatherapp.data.mappers.toWeatherInfo
import com.example.mvi_weatherapp.data.remote.WeatherApi
import com.example.mvi_weatherapp.domain.repostitory.WeatherRepository
import com.example.mvi_weatherapp.domain.util.Resource
import com.example.mvi_weatherapp.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getWeatherData(lat: Double, lng: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(
                api.getWeatherData(
                    lat = lat,
                    lng = lng
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}