package com.example.gamesessionapp.features.admin.management

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.gamesessionapp.R
import com.example.gamesessionapp.core.theme.DarkGray
import com.example.gamesessionapp.features.admin.management.cards.UserListItem
import com.example.gamesessionapp.features.admin.management.createUserForm.CreateUserDialog
import kotlinx.coroutines.launch


@Composable
fun AdminManagementScreen(
    modifier: Modifier = Modifier,
    component: AdminManagementComponent
) {

    val state by component.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.all_user_text),
                fontSize = 24.sp,
                color = Color.White
            )

            IconButton(
                onClick = {
                    coroutineScope.launch {
                        component.onIntent(AdminManagementIntent.OpenCreateUserDialog)
                    }
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_user_icon),
                    contentDescription = "Добавить пользователя",
                    tint = Color.White,
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Divider(
            color = DarkGray,
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f)
        ) {
            if (state.users.isEmpty()) {
                Text(
                    text = stringResource(R.string.all_user_not_found_text),
                    color = Color.DarkGray,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.users) { user ->
                        UserListItem(
                            user = user,
                            onBlockUser = {
                                coroutineScope.launch {
                                    component.onIntent(AdminManagementIntent.BlockUser(user.login))
                                }
                            },
                            onUnblockUser = {
                                coroutineScope.launch {
                                    component.onIntent(AdminManagementIntent.UnblockUser(user.login))
                                }
                            }
                        )
                        HorizontalDivider(
                            color = DarkGray,
                            thickness = 1.dp,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }

    if(state.showCreateUserDialog){
        Dialog(
            onDismissRequest = {
                coroutineScope.launch {
                    component.onIntent(AdminManagementIntent.CloseCreateUserDialog)
                }
            },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            CreateUserDialog(
                state = state,
                onIntent = { intent ->
                    coroutineScope.launch {
                        component.onIntent(intent)
                    }
                }
            )
        }
    }

}