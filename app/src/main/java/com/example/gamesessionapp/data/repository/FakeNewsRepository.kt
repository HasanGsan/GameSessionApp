package com.example.gamesessionapp.data.repository

import com.example.gamesessionapp.R
import com.example.gamesessionapp.data.models.NewsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object FakeNewsRepository : NewsRepository{

    private val readIds = mutableSetOf<String>()
    private val favoriteIds = mutableSetOf<String>()

    private val fakeNews = listOf(
        NewsData(
            id = "1",
            title = "Тайные улочки Барселоны",
            preview = R.drawable.spanish_image,
            content = "Lorem ipsum ...",
            timeCreated = 5,
            postCategory = "Пост",
            tags = "Культура"
        ),
        NewsData(
            id = "2",
            title = "Как проходит рабочий день PM из",
            preview = R.drawable.technocompany_image,
            content = "Lorem ipsum ...",
            timeCreated = 10,
            postCategory = "Эссе",
            tags = "Технологии"
        ),
        NewsData(
            id = "3",
            title = "Как я разрабатывал это приложение",
            preview = R.drawable.technocompany_image,
            content = "Lorem ipsum ...",
            timeCreated = 30,
            postCategory = "Статья",
            tags = "Путешествие"
        )
    )

    private val _newsFlow = MutableStateFlow<List<NewsData>>(emptyList())
    val newsflow: StateFlow<List<NewsData>> = _newsFlow.asStateFlow()

    suspend fun loadNews(){
        val news = getNews()
        _newsFlow.value = news
    }

    override suspend fun getNews(): List<NewsData> = _newsFlow.value

    override suspend fun getNewsByCategory(category: String): List<NewsData> =
        if (category == "Все") _newsFlow.value
        else _newsFlow.value.filter { it.postCategory == category }

    override suspend fun addFavorite(newsId: String) {
        favoriteIds.add(newsId)
        refreshNews(newsId)
    }

    override suspend fun removeFavorite(newsId: String) {
        favoriteIds.remove(newsId)
        refreshNews(newsId)
    }

    override suspend fun toggleFavorite(newsId: String) {
        if (favoriteIds.contains(newsId)) favoriteIds.remove(newsId)
        else favoriteIds.add(newsId)
        refreshNews(newsId)
    }

    override suspend fun getFavorites(): List<NewsData> =
        _newsFlow.value.filter { favoriteIds.contains(it.id) }

    override suspend fun isFavorite(newsId: String): Boolean = favoriteIds.contains(newsId)

    override suspend fun asRead(newsId: String) {
        if (readIds.contains(newsId)) readIds.remove(newsId)
        else readIds.add(newsId)
        refreshNews(newsId)
    }

    override suspend fun isRead(newsId: String): Boolean =
        readIds.contains(newsId)

    private fun refreshNews(newsId: String) {
        _newsFlow.value = _newsFlow.value.map { news ->
            if(news.id == newsId) news.copy() else news
        }
    }

}