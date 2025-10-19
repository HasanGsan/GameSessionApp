package com.example.gamesessionapp.features.user.weather

import com.example.gamesessionapp.data.models.WeatherData

data class WeatherState (
    val city: String = "",
    val degrees: String = "",
    val weatherDescription: String = "",
    val searchHistory: List<WeatherData> = emptyList(),
    val isFormValid: Boolean = false,
    val inputsVisible: Boolean = true,
    val buttonsVisible: Boolean = true
)