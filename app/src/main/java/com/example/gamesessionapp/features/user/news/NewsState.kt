package com.example.gamesessionapp.features.user.news

import com.example.gamesessionapp.data.uiState.items.NewsItemUiState

data class NewsState(
    val isLoading: Boolean = false,
    val newsItems: List<NewsItemUiState> = emptyList(),
    val errorMessage: String? = null,
    val openNewsId: String? = null
)