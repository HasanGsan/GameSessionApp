package com.example.gamesessionapp.features.admin.management

sealed class AdminManagementIntent {
    data class EnterNewUserLogin(val login: String) : AdminManagementIntent()
    data class EnterNewUserPassword(val password: String) : AdminManagementIntent()
    data class BlockUser(val login: String) : AdminManagementIntent()
    data class UnblockUser(val login: String) : AdminManagementIntent()
    object OpenCreateUserDialog : AdminManagementIntent()
    object CloseCreateUserDialog : AdminManagementIntent()
    object SubmitCreateUser : AdminManagementIntent()
}