package com.example.gamesessionapp.features.admin.sessions.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamesessionapp.R
import com.example.gamesessionapp.features.admin.sessions.SessionWithUsers

@Composable
fun SessionListItem (
    modifier: Modifier = Modifier,
    sessionWithUser: SessionWithUsers
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = sessionWithUser.session.timeRange,
                color = Color.White,
                fontSize = 14.sp
            )

            if (sessionWithUser.users.isNotEmpty()) {
                sessionWithUser.users.forEach { user ->
                    UserItemWithAvatar(
                        user = user,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            } else {
                Text(
                    text = stringResource(R.string.text_not_users),
                    color = Color.DarkGray,
                    fontSize = 12.sp
                )
            }
        }
    }
}
