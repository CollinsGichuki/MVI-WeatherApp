package com.example.mvi_weatherapp.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvi_weatherapp.domain.location.LocationTracker
import com.example.mvi_weatherapp.domain.repostitory.WeatherRepository
import com.example.mvi_weatherapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherInfo() {
        Log.d("Trackking", "loading weatherInfo.")

        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                isError = null
            )

            locationTracker.getCurrentLocation()?.let { location ->
                Log.d("Trackking", "current location: $location")

                when (val result =
                    repository.getWeatherData(location.latitude, location.longitude)) {
                    is Resource.Success -> {
                        Log.d("Trackking", "resource successful. Result: $result")
                        state = state.copy(
                            isLoading = false,
                            weatherInfo = result.data,
                            isError = null
                        )
                    }

                    is Resource.Error -> {
                        Log.d("Trackking", "resource error: Error: ${result.message}")
                        state = state.copy(
                            isLoading = false,
                            weatherInfo = null,
                            isError = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                Log.d("Trackking", "location permission not found")
                state = state.copy(
                    isLoading = false,
                    isError = "Couldn't retrieve location. Make sure you've granted the location permission and enabled GPS"
                )
            }
        }
    }
}