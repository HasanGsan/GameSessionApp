package com.example.gamesessionapp.features.admin.sessions.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamesessionapp.R
import com.example.gamesessionapp.data.local.entity.user.UserEntity
import com.example.gamesessionapp.features.admin.management.component.UserAvatar

@Composable
fun UserSelectionItem(
    modifier: Modifier = Modifier,
    user: UserEntity,
    isSelected: Boolean,
    onSelectionChange: (Boolean) -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(if(isSelected) Color.DarkGray else Color.Transparent)
            .border(1.dp, Color.DarkGray)
            .clickable { onSelectionChange(!isSelected) }
            .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
    ) {

        UserAvatar(
            username = user.login,
            size = 40.dp,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(12.dp))
        Spacer(modifier = Modifier.width(6.dp))

        Column(
            modifier = Modifier.weight(1f)
        ){
            Text(
                text = user.login,
                color = Color.White,
                fontSize = 16.sp,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if(user.isOnline) "Онлайн" else "Оффлайн",
                    color = if(user.isOnline) Color.Green else Color.Gray,
                    fontSize = 14.sp
                )
            }
        }

        Checkbox(
            checked = isSelected,
            onCheckedChange = onSelectionChange
        )
    }
}