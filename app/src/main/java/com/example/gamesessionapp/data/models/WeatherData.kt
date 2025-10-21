package com.example.gamesessionapp.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherData (
    val city: String = "",
    val degrees: String = "",
    val weatherDescription: String = "",
) : Parcelable