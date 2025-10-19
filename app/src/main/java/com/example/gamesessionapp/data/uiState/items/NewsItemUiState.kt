package com.example.gamesessionapp.data.uiState.items

import com.example.gamesessionapp.data.models.NewsData

data class NewsItemUiState(
    val newsData: NewsData,
    val isRead: Boolean = false,
    val isFavorite: Boolean = false
)