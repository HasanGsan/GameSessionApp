package com.example.gamesessionapp.features.user.weather.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamesessionapp.R
import com.example.gamesessionapp.data.models.WeatherData
import com.example.gamesessionapp.core.theme.DarkGray
import com.example.gamesessionapp.core.theme.DeepDarkGray
import kotlin.text.uppercase

@Composable
fun SearchHistoryCard(
    searchHistory: List<WeatherData>,
    onHistoryItemClick: (WeatherData) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.historyCard_title),
        color = Color.White,
        fontSize = 18.sp,
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
    )

    Spacer(Modifier.height(32.dp))

    if (searchHistory.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = DeepDarkGray,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.historyCard_description),
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 14.sp,
            )
        }
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            searchHistory.forEach { weatherData ->
                HistoryItem(
                    weatherData = weatherData,
                    onHistoryItemClick = onHistoryItemClick,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun HistoryItem(
    weatherData: WeatherData,
    onHistoryItemClick: (WeatherData) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = DeepDarkGray,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable { onHistoryItemClick(weatherData) }
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = DarkGray,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            ) {
                Text(
                    text = weatherData.city.uppercase(),
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
            Row(
                modifier = Modifier.padding(end = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.degrees_icon),
                    contentDescription = stringResource(R.string.input_degrees_name),
                    tint = Color.White,
                    modifier = Modifier.size(12.dp),
                )
                Text(
                    text = weatherData.degrees,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}