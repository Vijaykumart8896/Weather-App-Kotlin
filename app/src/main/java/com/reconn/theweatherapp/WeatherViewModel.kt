package com.reconn.theweatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ViewModel class
class WeatherViewModel : ViewModel() {

    // StateFlow to store weather data
    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData

    // Instance of WeatherApi
    private val weatherApi = WeatherApi.create()

    // Function to fetch weather data from the API
    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                // Make API call to fetch weather data
                val response = weatherApi.getWeather(city, apiKey)
                _weatherData.value = response
            } catch (e: Exception) {
                // Handle any errors
                e.printStackTrace()
            }
        }
    }
}