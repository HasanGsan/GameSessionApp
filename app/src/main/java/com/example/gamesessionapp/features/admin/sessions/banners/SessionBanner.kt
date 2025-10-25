package com.example.gamesessionapp.features.admin.sessions.banners

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamesessionapp.R
import com.example.gamesessionapp.data.local.entity.session.SessionEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable // Не успел это реализовать, но решил не убирать
fun SessionBanner(
    modifier: Modifier = Modifier,
    session: SessionEntity
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Color(0xFF2C2C2C),
                RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.time_icon),
                contentDescription = "Время сессии",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "Ваша сессия длится с ${formatTime(session.startTime)} до ${formatEndTime(session.startTime, session.duration)}",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}

private fun formatTime(time: LocalDateTime): String {
    return time.format(DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()))
}

private fun formatEndTime(startTime: LocalDateTime, durationHours: Int): String {
    val endTime = startTime.plusHours(durationHours.toLong())
    return endTime.format(DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()))
}

