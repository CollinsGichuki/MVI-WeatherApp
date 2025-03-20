package com.example.mvi_weatherapp.presentation.views

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mvi_weatherapp.presentation.WeatherState

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherForecast(
    state: WeatherState,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    state.weatherInfo?.weatherDataPerDay?.get(0)?.let { data ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(text = "Today", fontSize = 20.sp, color = Color.White)

            Spacer(modifier = Modifier.height(16.dp))
            LazyRow(
                state = listState
            ) {
                items(data) { weatherData ->
                    HourlyWeatherDisplay(
                        weatherData = weatherData,
                        modifier = modifier
                            .height(100.dp)
                            .padding(horizontal = 16.dp)
                    )
                }
            }

            LaunchedEffect(state.weatherInfo.weatherDataPerDay) {
                val currentTime = state.weatherInfo.weatherDataPerDay.values
                    .flatten()
                    .find { it.time == state.weatherInfo.currentWeatherData?.time }
                listState.animateScrollToItem(data.indexOf(currentTime))
            }
        }
    }
}