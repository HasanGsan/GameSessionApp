package com.example.gamesessionapp.features.user.favorites


import com.arkivanov.decompose.ComponentContext
import com.example.gamesessionapp.data.repository.NewsRepository
import com.example.gamesessionapp.data.uiState.items.NewsItemUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteComponent(
    componentContext: ComponentContext,
    private val repository: NewsRepository
) {

    private val _state = MutableStateFlow(FavoriteState())
    val state: StateFlow<FavoriteState> = _state.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)

    fun onIntent(intent: FavoriteIntent) {
        when (intent) {
            is FavoriteIntent.LoadFavorites -> loadFavorites()
            is FavoriteIntent.SelectTag -> selectTag(intent.tag)
            is FavoriteIntent.ToggleFavorite -> toggleFavorite(intent.newsId)
        }
    }

    private fun loadFavorites() {
        scope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val favorites = repository.getFavorites()
                val tags = repository.getNews().map { it.tags }.distinct()
                _state.value = _state.value.copy(
                    favoriteItems = favorites.map { NewsItemUiState(it, true) },
                    allTags = listOf("Все") + tags,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Ошибка загрузки"
                )
            }
        }
    }

    private fun toggleFavorite(newsId: String) {
        scope.launch {
            repository.toggleFavorite(newsId)
            updateFavorites()
        }
    }

    private fun selectTag(tag: String) {
        _state.value = _state.value.copy(selectedTag = tag)
    }

    private fun updateFavorites() {
        scope.launch {
            val allFavorites = repository.getFavorites()
            val filtered = if (_state.value.selectedTag == "Все") {
                allFavorites
            } else {
                allFavorites.filter { it.tags.contains(_state.value.selectedTag) }
            }
            _state.value = _state.value.copy(
                favoriteItems = filtered.map { NewsItemUiState(it, true) }
            )
        }
    }

}