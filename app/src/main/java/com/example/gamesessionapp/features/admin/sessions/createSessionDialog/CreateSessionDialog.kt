package com.example.gamesessionapp.features.admin.sessions.createSessionDialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamesessionapp.R
import com.example.gamesessionapp.features.admin.sessions.AdminSessionsIntent
import com.example.gamesessionapp.features.admin.sessions.AdminSessionsState
import com.example.gamesessionapp.features.admin.sessions.buttons.ButtonCreateSession
import com.example.gamesessionapp.features.admin.sessions.cards.UserSelectionItem
import com.example.gamesessionapp.features.admin.sessions.inputs.DateInput
import com.example.gamesessionapp.features.admin.sessions.inputs.DurationInput
import com.example.gamesessionapp.features.admin.sessions.inputs.TimeInput

@Composable
fun CreateSessionDialog (
    modifier: Modifier = Modifier,
    state: AdminSessionsState,
    onIntent: (AdminSessionsIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF1a1a1a))
            .padding(16.dp)
    ) {
        Text(
            modifier = modifier.fillMaxWidth(),
            text = stringResource(id = R.string.title_session_create),
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            DateInput(
                value = state.date,
                onValueChange = { onIntent(AdminSessionsIntent.UpdateDate(it)) },
                onCalendarClick = {
                    TODO()
                }
            )

            TimeInput(
                value = state.time,
                onValueChange = { onIntent(AdminSessionsIntent.UpdateTime(it)) },
                onTimeClick = {
                    TODO()
                },
            )

            DurationInput(
                value = state.duration,
                onValueChange = { onIntent(AdminSessionsIntent.UpdateDuration(it)) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.all_user_title),
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .background(Color(0xFF1a1a1a))
                .padding(8.dp)
        ) {
            items(state.availableUsers.size) { index ->
                val user = state.availableUsers[index]
                UserSelectionItem(
                    user = user,
                    isSelected = state.selectedUsers.contains(user.login),
                    onSelectionChange = { selected ->
                        if(selected){
                            onIntent(AdminSessionsIntent.ToggleUserSelection(user.login))
                        } else {
                            onIntent(AdminSessionsIntent.ToggleUserSelection(user.login))
                        }
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            ButtonCreateSession(
                isEnabled = state.isFormValid,
                onClick = { onIntent(AdminSessionsIntent.CreateSession) }
            )
        }
    }
}
