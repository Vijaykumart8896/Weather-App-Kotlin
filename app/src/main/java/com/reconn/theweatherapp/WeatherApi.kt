package com.reconn.theweatherapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Retrofit API interface
interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric" // Default: Celsius
    ): WeatherResponse

    companion object {
        fun create(): WeatherApi {
            // Create Retrofit instance
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/") // Base URL for OpenWeather API
                .addConverterFactory(GsonConverterFactory.create()) // JSON parsing using Gson
                .build()

            // Return the API instance
            return retrofit.create(WeatherApi::class.java)
        }
    }
}