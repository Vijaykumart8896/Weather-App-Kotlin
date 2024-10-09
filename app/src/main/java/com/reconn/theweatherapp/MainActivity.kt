package com.reconn.theweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reconn.theweatherapp.ui.theme.TheWeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheWeatherAppTheme {
                WeatherScreen()
            }
        }
    }
}

@Composable
fun WeatherScreen() {
    // ViewModel instance
    val viewModel: WeatherViewModel = viewModel()
    val weatherData by viewModel.weatherData.collectAsState()

    var city by remember { mutableStateOf("") }
    var isWeatherFetched by remember { mutableStateOf(false) } // Track if weather has been fetched
    val apiKey = "096a6e2a4b31ec19658096a0df9c87c1"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.weatherbkg), // Ensure this drawable exists
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // TextField for user input (city name)
            BasicTextField(
                value = city,
                onValueChange = { city = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.White, MaterialTheme.shapes.small)
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    if (city.isEmpty()) {
                        Text(
                            text = "Enter City Name",
                            color = Color.Gray,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    innerTextField()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons for Check Weather and Clear
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    if (city.isNotBlank()) {
                        viewModel.fetchWeather(city, apiKey)
                        isWeatherFetched = true // Set flag to true after fetching
                    }
                }) {
                    Text("Check Weather")
                }
                Button(onClick = {
                    city = ""
                    viewModel.fetchWeather("", apiKey) // Clear the weather data as well
                    isWeatherFetched = false // Reset the flag
                }) {
                    Text("Clear")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display weather data in separate cards
            if (isWeatherFetched) {
                weatherData?.let {
                    Column {
                        // Card for City Name
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            shape = MaterialTheme.shapes.medium // Rounded corners
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center // Centering items
                            ) {
                                Icon(
                                    imageVector = Icons.Default.LocationOn,
                                    contentDescription = "City",
                                    tint = Color.Blue // Change to blue
                                )
                                Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
                                Text(
                                    text = it.name,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontSize = 20.sp,
                                        color = Color.Blue // Change to blue
                                    ),
                                    textAlign = TextAlign.Center // Center text
                                )
                            }
                        }

                        // Card for Temperature
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            shape = MaterialTheme.shapes.medium // Rounded corners
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center // Centering items
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Temperature",
                                    tint = Color.Blue // Change to blue
                                )
                                Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
                                Text(
                                    text = "${it.main.temp}°C",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontSize = 20.sp,
                                        color = Color.Blue // Change to blue
                                    ),
                                    textAlign = TextAlign.Center // Center text
                                )
                            }
                        }

                        // Card for Humidity
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            shape = MaterialTheme.shapes.medium // Rounded corners
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center // Centering items
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = "Humidity",
                                    tint = Color.Blue // Change to blue
                                )
                                Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
                                Text(
                                    text = "Humidity: ${it.main.humidity}%",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontSize = 20.sp,
                                        color = Color.Blue // Change to blue
                                    ),
                                    textAlign = TextAlign.Center // Center text
                                )
                            }
                        }

                        // Card for Weather Description
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            shape = MaterialTheme.shapes.medium // Rounded corners
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center // Centering items
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "Weather Description",
                                    tint = Color.Blue // Change to blue
                                )
                                Spacer(modifier = Modifier.width(8.dp)) // Space between icon and text
                                Text(
                                    text = it.weather[0].description.capitalize(),
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontSize = 20.sp,
                                        color = Color.Blue // Change to blue
                                    ),
                                    textAlign = TextAlign.Center // Center text
                                )
                            }
                        }
                    }
                } ?: run {
                    Text(
                        text = "No data available. Enter a city name.",
                        color = Color.Blue // Change to blue
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherPreview() {
    TheWeatherAppTheme {
        WeatherScreen()
    }
}