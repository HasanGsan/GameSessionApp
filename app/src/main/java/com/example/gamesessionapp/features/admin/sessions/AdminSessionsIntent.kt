package com.example.gamesessionapp.features.admin.sessions

sealed class AdminSessionsIntent {
    object OpenCreateSessionDialog : AdminSessionsIntent()
    object CloseCreateSessionDialog : AdminSessionsIntent()
    data class UpdateDate(val date: String) : AdminSessionsIntent()
    data class UpdateTime(val time: String) : AdminSessionsIntent()
    data class UpdateDuration(val duration: String) : AdminSessionsIntent()
    data class ToggleUserSelection(val userLogin: String) : AdminSessionsIntent()
    object CreateSession : AdminSessionsIntent()
}