package com.example.gamesessionapp.data.repository.room

import com.example.gamesessionapp.data.local.dao.NewsStatusDao
import com.example.gamesessionapp.data.local.entity.FavoriteEntity
import com.example.gamesessionapp.data.local.entity.ReadPostEntity
import com.example.gamesessionapp.data.models.NewsData
import com.example.gamesessionapp.data.repository.FakeNewsRepository
import com.example.gamesessionapp.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first

class RoomNewsRepository(
    private val newsStatusDao: NewsStatusDao,
    private val fakeNewsRepository: FakeNewsRepository = FakeNewsRepository

) : NewsRepository {

    private val _newsFlow = MutableStateFlow<List<NewsData>>(emptyList())
    val newsFlow: StateFlow<List<NewsData>> = _newsFlow.asStateFlow()

    suspend fun loadNews(){
        val news = FakeNewsRepository.getNews()
        _newsFlow.value = news
    }

    override suspend fun getNews(): List<NewsData> = _newsFlow.value

    override suspend fun getNewsByCategory(category: String): List<NewsData> =
        if(category == "Все") _newsFlow.value
        else _newsFlow.value.filter { it.postCategory == category }

    override suspend fun addFavorite(newsId: String) {
        newsStatusDao.addFavorite(FavoriteEntity(newsId))
        refreshNews(newsId)
    }

    override suspend fun removeFavorite(newsId: String) {
        newsStatusDao.removeFavorite(newsId)
        refreshNews(newsId)
    }

    override suspend fun getFavorites(): List<NewsData> {
        val favoriteIds = newsStatusDao.getAllFavorites().first().map { it.newsId }
        return _newsFlow.value.filter { it.id in favoriteIds }
    }

    override suspend fun toggleFavorite(newsId: String) {
        val isFav = newsStatusDao.isFavorite(newsId) > 0
        if (isFav) newsStatusDao.removeFavorite(newsId)
        else newsStatusDao.addFavorite(FavoriteEntity(newsId))
        refreshNews(newsId)
    }

    override suspend fun isFavorite(newsId: String): Boolean =
        newsStatusDao.isFavorite(newsId) > 0

    override suspend fun asRead(newsId: String) {
        val isNowRead =  newsStatusDao.isRead(newsId) > 0
        if (isNowRead) newsStatusDao.removeReadPost(newsId)
        else newsStatusDao.addReadPost(ReadPostEntity(newsId))
        refreshNews(newsId)
    }

    override suspend fun isRead(newsId: String): Boolean =
        newsStatusDao.isRead(newsId) > 0

    private fun refreshNews(newsId: String) {
        _newsFlow.value = _newsFlow.value.map { news ->
            if(news.id == newsId) news.copy() else news
        }
    }
}