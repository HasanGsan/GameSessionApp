package com.example.gamesessionapp.features.user.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamesessionapp.R
import com.example.gamesessionapp.features.user.news.cards.NewsCard
import kotlinx.coroutines.launch

@Composable
fun NewsScreen (component: NewsComponent) {
    val state by component.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        when {
            state.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }

            state.errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Ошибка: ${state.errorMessage}",
                        color = Color.Red,
                    )
                }
            }
        else -> {
            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = stringResource(R.string.screen_news_title),
                        color = Color.White,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                items(state.newsItems) { item ->
                    NewsCard(
                        newsItem = item,
                        onToggleRead = { id ->
                            coroutineScope.launch {
                                component.onIntent(NewsIntent.ToggleRead(id))
                            }
                        },
                        onOpen = { id ->
                            coroutineScope.launch {
                                component.onIntent(NewsIntent.OpenNews(id))
                            }
                        },
                        onToggleFavorite = { id ->
                            coroutineScope.launch {
                                component.onIntent(NewsIntent.ToggleFavorite(id))
                            }
                        }
                    )
                }
            }
          }
        }
    }
}