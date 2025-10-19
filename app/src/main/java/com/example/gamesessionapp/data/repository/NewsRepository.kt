package com.example.gamesessionapp.data.repository

import com.example.gamesessionapp.data.models.NewsData

interface NewsRepository {
    suspend fun getNews() : List<NewsData>
    suspend fun getNewsByCategory(category: String): List<NewsData>
    suspend fun addFavorite(newsId: String)
    suspend fun removeFavorite(newsId: String)
    suspend fun getFavorites(): List<NewsData>

    //Room
    suspend fun toggleFavorite(newsId: String)
    suspend fun isFavorite(newsId: String) : Boolean
    suspend fun asRead(newsId: String)
    suspend fun isRead(newsId: String) : Boolean
}
