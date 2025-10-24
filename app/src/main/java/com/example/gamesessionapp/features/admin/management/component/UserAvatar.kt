package com.example.gamesessionapp.features.admin.management.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.absoluteValue
import kotlin.random.Random

@Composable
fun UserAvatar(
    modifier: Modifier = Modifier,
    username: String,
    size: Dp = 40.dp,
    fontSize: TextUnit = 16.sp,
) {
    val firstLetter = username.firstOrNull()?.uppercaseChar() ?: '?'
    val backgroundColor = getAvatarColor(username)

    Box(
        modifier = Modifier
            .size(size)
            .background(backgroundColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = firstLetter.toString(),
            color = Color.White,
            fontSize = fontSize
        )
    }
}

private fun getAvatarColor(username: String): Color {
    val colors = listOf(
        Color(0xFFF44336),
        Color(0xFFE91E63),
        Color(0xFF9C2780),
        Color(0xFF673AB7),
        Color(0xFF3F51B5),
        Color(0xFF2196F3),
        Color(0xFF03A9F4),
        Color(0xFF00BCD4),
        Color(0xFF009688),
        Color(0xFF4CAF50),
        Color(0xFF8BC34A),
        Color(0xFFCDDC39),
        Color(0xFFFFC107),
        Color(0xFFFF9800),
        Color(0xFFFF5722),
    )

    val index = if (username.isNotEmpty()) {
        username.hashCode().absoluteValue % colors.size
    } else {
        Random.nextInt(colors.size)
    }

    return colors[index]
}

