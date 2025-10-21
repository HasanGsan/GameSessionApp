package com.example.gamesessionapp.features.user.weather

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.statekeeper.consume
import com.example.gamesessionapp.data.models.WeatherData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class WeatherComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val initialState: WeatherState =
            componentContext.stateKeeper.consume<WeatherState>("weather_state") ?: WeatherState()

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<WeatherState> = _state

    init {
        componentContext.stateKeeper.register("weather_state") { _state.value }
    }

    fun onIntent(intent: WeatherIntent) {

        when(intent) {

            is WeatherIntent.UpdateCity -> {
                _state.update { it.copy(city = intent.city) }
                updateFormValidity()
            }

            is WeatherIntent.UpdateDegrees -> {
                _state.update { it.copy(degrees = intent.degrees) }
                updateFormValidity()
            }

            WeatherIntent.GenerateDescription -> {
                val city = _state.value.city
                val degrees = _state.value.degrees.toIntOrNull() ?: 0
                val description = when (degrees) {
                    in -50..14 -> "Сейчас в г. $city Холодно"
                    in 15..24 -> "Сейчас в г. $city Нормально"
                    in 25..50 -> "Сейчас в г. $city Жарко"
                    else -> "Сейчас в г. $city Катастрофа"
                }
                _state.update {
                    it.copy(
                        weatherDescription = description,
                        degrees = "${_state.value.degrees}°C"
                    )
                }
            }

            WeatherIntent.AddToHistory -> {
                val currentHistory = _state.value.searchHistory.toMutableList()
                currentHistory.removeAll { it.city == _state.value.city }
                currentHistory.add(0, WeatherData(
                    city = _state.value.city,
                    degrees = _state.value.degrees,
                    weatherDescription = _state.value.weatherDescription)
                )
                if(currentHistory.size > 5) currentHistory.removeAt(currentHistory.lastIndex)
                _state.update { it.copy(searchHistory = currentHistory) }
            }

            WeatherIntent.OnBlankWeatherClick -> {
                onIntent(WeatherIntent.GenerateDescription)
                onIntent(WeatherIntent.HideInputs)
            }

            WeatherIntent.ClearAndCreateNewRequest -> {

                if(_state.value.isFormValid) {
                    onIntent(WeatherIntent.AddToHistory)
                }

                onIntent(WeatherIntent.ShowInputs)
                _state.update {
                    it.copy( city = "", degrees = "", weatherDescription = "", isFormValid = false, searchHistory = it.searchHistory)
                }
            }

            WeatherIntent.HideInputs -> {
                _state.update { it.copy(inputsVisible = false, buttonsVisible = false) }
            }

            WeatherIntent.ShowInputs -> {
                _state.update { it.copy(inputsVisible = true, buttonsVisible = true) }
            }

        }

    }

    private fun updateFormValidity() {
        val cityValid = _state.value.city.isNotEmpty()
        val degreesValid = _state.value.degrees.isNotEmpty()
        _state.update { it.copy(isFormValid = cityValid && degreesValid) }
    }
}