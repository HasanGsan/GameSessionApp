package com.example.gamesessionapp.features.auth

sealed class AuthIntent {
    data class EnterLogin(val login: String) : AuthIntent()
    data class EnterPassword(val password: String) : AuthIntent()
    object TogglePasswordVisibility : AuthIntent()
    object SubmitLogin : AuthIntent()
}