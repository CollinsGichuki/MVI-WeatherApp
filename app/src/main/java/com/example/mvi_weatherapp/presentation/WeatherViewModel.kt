package com.example.mvi_weatherapp.presentation

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
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                isError = null
            )

            locationTracker.getCurrentLocation()?.let { location ->
                when (val result =
                    repository.getWeatherData(location.latitude, location.longitude)) {
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            weatherInfo = result.data,
                            isError = null
                        )
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            weatherInfo = null,
                            isError = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                state = state.copy(
                    isLoading = false,
                    isError = "Couldn't retrieve location. Make sure you've granted the location permission and enabled GPS"
                )
            }
        }
    }
}