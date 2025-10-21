package com.example.gamesessionapp.features.user.news

import com.arkivanov.decompose.ComponentContext
import com.example.gamesessionapp.data.repository.FakeNewsRepository
import com.example.gamesessionapp.data.repository.NewsRepository
import com.example.gamesessionapp.data.uiState.items.NewsItemUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsComponent(
    componentContext: ComponentContext,
    private val repository: NewsRepository,
) : ComponentContext by componentContext {

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(NewsState())
    val state : StateFlow<NewsState> = _state

    init {
        coroutineScope.launch {
            onIntent(NewsIntent.LoadNews)
        }
    }

     suspend fun onIntent(intent: NewsIntent) {
        when (intent) {
            is NewsIntent.LoadNews -> loadNews()
            is NewsIntent.ToggleRead -> toggleRead(intent.newsId)
            is NewsIntent.ToggleFavorite -> toggleFavorite(intent.newsId)
            is NewsIntent.OpenNews -> _state.update { it.copy(openNewsId = intent.newsId) }
            is NewsIntent.CloseNews -> _state.update { it.copy(openNewsId = null) }
        }
    }

    private suspend fun loadNews() {
        _state.update { it.copy(isLoading = true, errorMessage = null) }

        try {
            val list = repository.getNews()
            val items = list.map {newsData ->
                NewsItemUiState(
                    newsData = newsData,
                    isRead = repository.isRead(newsData.id),
                    isFavorite = repository.isFavorite(newsData.id)
                )
            }
            _state.update { it.copy(isLoading = false, newsItems = items) }
        } catch (e: Throwable) {
            _state.update { it.copy(isLoading = false, errorMessage = e.message) }
        }
    }

    private suspend fun toggleRead(newsId: String) {
        repository.asRead(newsId)
        val nowRead = repository.isRead(newsId)
        _state.update { state ->
            val updated = state.newsItems.map {
                if (it.newsData.id == newsId) it.copy(isRead = nowRead) else it
            }
            state.copy(newsItems = updated)
        }
    }

    private suspend fun toggleFavorite(newsId: String) {
        repository.addFavorite(newsId)
        _state.update { state ->
            val updated = state.newsItems.map {
                if (it.newsData.id == newsId) it.copy(isFavorite = !it.isFavorite) else it
            }
            state.copy(newsItems = updated)
        }
    }
}

