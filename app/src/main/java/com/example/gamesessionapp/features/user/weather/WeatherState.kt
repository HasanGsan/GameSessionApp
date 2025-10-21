package com.example.gamesessionapp.features.user.weather

import android.os.Parcelable
import com.example.gamesessionapp.data.models.WeatherData
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherState (
    val city: String = "",
    val degrees: String = "",
    val weatherDescription: String = "",
    val searchHistory: List<WeatherData> = emptyList(),
    val isFormValid: Boolean = false,
    val inputsVisible: Boolean = true,
    val buttonsVisible: Boolean = true
) : Parcelable