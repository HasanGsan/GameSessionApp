package com.example.gamesessionapp.data.uiState

import com.example.gamesessionapp.data.uiState.items.NewsItemUiState


data class NewsUiState (
    val isLoading: Boolean = false,
    val newsItems: List<NewsItemUiState> = emptyList(),
    val errorMessage: String? = null,
)