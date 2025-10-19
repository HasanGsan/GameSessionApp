package com.example.gamesessionapp.features.user.favorites

import com.example.gamesessionapp.data.uiState.items.NewsItemUiState

data class FavoriteState (
    val favoriteItems: List<NewsItemUiState> = emptyList(),
    val selectedTag: String = "Все",
    val allTags: List<String> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)