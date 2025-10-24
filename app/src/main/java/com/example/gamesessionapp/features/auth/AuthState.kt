package com.example.gamesessionapp.features.auth

import com.example.gamesessionapp.data.local.entity.user.UserRole

data class AuthState(
    val login: String = "",
    val password: String = "",
    val passwordVisible: Boolean = false,
    val loginError: String = "",
    val passwordError: String = "",
    val isFormValid: Boolean = false,
    val authSuccess: Boolean = false,
    val authMessage: String = "",
    val userRole: UserRole? = null
)