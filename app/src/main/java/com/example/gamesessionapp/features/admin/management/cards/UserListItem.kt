package com.example.gamesessionapp.features.admin.management.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamesessionapp.R
import com.example.gamesessionapp.data.local.entity.user.UserEntity
import com.example.gamesessionapp.features.admin.management.component.UserAvatar

@Composable
fun UserListItem (
    modifier: Modifier = Modifier,
    user: UserEntity,
    onBlockUser: () -> Unit,
    onUnblockUser: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserAvatar(
            username = user.login,
            size = 48.dp,
            fontSize = 16.sp,
        )

        Spacer(modifier = Modifier.width(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if(user.isBlocked) "Заблокированный \nюзер" else user.login,
                color = if(user.isBlocked) Color.Red else Color.White,
                fontSize = 14.sp
            )

            Text(
                text = if(user.isOnline) "Онлайн" else "Оффлайн",
                color = if(user.isOnline) Color.Green else Color.Gray,
                fontSize = 14.sp
            )

            IconButton(
                onClick = {
                    if(user.isBlocked) { onUnblockUser() } else { onBlockUser() }
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = painterResource(
                        id = if(user.isBlocked) { R.drawable.button_unblock_icon } else { R.drawable.button_block_icon }
                    ),
                    contentDescription = if (user.isBlocked) "Разблокировать" else "Заблокировать",
                    tint = Color.Unspecified
                )

            }
        }
    }
}