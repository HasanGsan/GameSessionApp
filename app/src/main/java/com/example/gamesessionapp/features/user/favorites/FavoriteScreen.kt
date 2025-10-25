package com.example.gamesessionapp.features.user.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamesessionapp.R
import com.example.gamesessionapp.features.user.favorites.cards.favoriteCard


@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    component: FavoriteComponent
) {
    val uiState = component.state.collectAsState().value

    LaunchedEffect(Unit) {
        component.onIntent(FavoriteIntent.LoadFavorites)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(10.dp)
    ){
        Text(
            text = stringResource(R.string.title_favorite_text),
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            uiState.allTags.forEach { tag ->
                val isSelected = tag == uiState.selectedTag
                Box(
                    modifier = Modifier
                        .border(1.dp, Color.Gray, RoundedCornerShape(3.dp))
                        .clickable { component.onIntent(FavoriteIntent.SelectTag(tag)) }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .background(if (isSelected) Color.DarkGray else Color.Transparent)
                ) {
                    Text(text = tag, color = if(isSelected) Color.White else Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)){
            items(uiState.favoriteItems, key = {it.newsData.id}) { newsItem ->
                favoriteCard(
                    newsItem = newsItem,
                    onToggleFavorite = { id ->
                        component.onIntent(FavoriteIntent.ToggleFavorite(id))
                    }
                )
            }
        }
    }
}