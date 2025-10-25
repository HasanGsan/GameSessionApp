package com.example.gamesessionapp.features.admin.sessions.cards

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamesessionapp.data.local.entity.user.UserEntity
import com.example.gamesessionapp.features.admin.management.component.UserAvatar

@Composable
fun UserItemWithAvatar(
    modifier: Modifier = Modifier,
    user: UserEntity
){
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){

        UserAvatar(
            username = user.login,
            size = 32.dp,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.size(12.dp))

        Text(
            text = user.login,
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = if (user.isOnline) "Онлайн" else "Оффлайн",
                color = if (user.isOnline) Color.Green else Color.Gray,
                fontSize = 12.sp
            )
        }

    }
}