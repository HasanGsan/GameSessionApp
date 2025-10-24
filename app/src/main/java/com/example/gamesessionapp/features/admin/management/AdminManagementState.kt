package com.example.gamesessionapp.features.admin.management

import com.example.gamesessionapp.data.local.entity.user.UserEntity

data class AdminManagementState (
    val showCreateUserDialog: Boolean = false,
    val newUserLogin: String = "",
    val newUserPassword: String = "",
    val newUserLoginError: String = "",
    val newUserPasswordError: String = "",
    val isCreateUserFormValid: Boolean = false,
    val users: List<UserEntity> = emptyList()
)