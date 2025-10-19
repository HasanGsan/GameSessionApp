package com.example.gamesessionapp.features.user.news

sealed interface NewsIntent {
    data object LoadNews : NewsIntent
    data class ToggleRead(val newsId: String) : NewsIntent
    data class ToggleFavorite(val newsId: String) : NewsIntent
    data class OpenNews(val newsId: String) : NewsIntent
    data object CloseNews : NewsIntent
}