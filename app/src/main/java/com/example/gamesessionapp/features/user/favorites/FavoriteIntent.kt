package com.example.gamesessionapp.features.user.favorites

sealed class FavoriteIntent {
    data class SelectTag(val tag: String) : FavoriteIntent()
    data class ToggleFavorite(val newsId: String) : FavoriteIntent()
    object LoadFavorites : FavoriteIntent()
}