package com.reconn.theweatherapp

// Data class to represent the response from the weather API
data class WeatherResponse(
    val name: String, // City name
    val main: Main, // Main weather data (temperature, pressure, etc.)
    val weather: List<Weather> // List of weather conditions
)

data class Main(
    val temp: Float, // Temperature
    val pressure: Int, // Atmospheric pressure
    val humidity: Int // Humidity level
)

data class Weather(
    val description: String // Weather condition description (e.g., "clear sky")
)