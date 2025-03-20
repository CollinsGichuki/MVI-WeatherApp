package com.example.mvi_weatherapp

import com.example.mvi_weatherapp.data.mappers.toWeatherInfo
import com.example.mvi_weatherapp.data.remote.WeatherApi
import com.example.mvi_weatherapp.data.remote.WeatherDataDto
import com.example.mvi_weatherapp.data.remote.WeatherDto
import com.example.mvi_weatherapp.data.repository.WeatherRepositoryImpl
import com.example.mvi_weatherapp.domain.repostitory.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

class WeatherRepositoryImplTest {
    private val fakeApi: WeatherApi = mock()
    private val timeNow = LocalDateTime.now().toString()

    private val fakeWeatherRepository: WeatherRepository = WeatherRepositoryImpl(fakeApi)

    @Test
    fun `getWeatherData should return success when api call is successful`(): Unit = runBlocking {
        // Given
        val lat = 10.0
        val lng = 20.0
        val weatherDataDto = WeatherDataDto(
            time = listOf(timeNow),
            temperatures = listOf(23.0),
            weatherCodes = listOf(23),
            pressures = listOf(23.0),
            windSpeeds = listOf(23.0),
            humidities = listOf(23.0)
        )
        val weatherInfo = WeatherDto(weatherDataDto).toWeatherInfo()

        // When
        whenever(fakeApi.getWeatherData(any(), any())) doReturn WeatherDto(weatherDataDto)
        val result = fakeWeatherRepository.getWeatherData(lat, lng)

        // Then
        result.data shouldBeEqualTo weatherInfo
    }
}