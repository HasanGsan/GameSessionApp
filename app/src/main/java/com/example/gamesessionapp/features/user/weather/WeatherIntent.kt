package com.example.gamesessionapp.features.user.weather

sealed interface WeatherIntent {

    data class UpdateCity(val city: String) : WeatherIntent
    data class UpdateDegrees(val degrees: String) : WeatherIntent
    object GenerateDescription : WeatherIntent
    object AddToHistory : WeatherIntent
    object OnBlankWeatherClick : WeatherIntent
    object ClearAndCreateNewRequest : WeatherIntent
    object ShowInputs : WeatherIntent
    object HideInputs : WeatherIntent

}