package com.example.gamesessionapp.app.bottomNavBar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.example.gamesessionapp.R
import com.example.gamesessionapp.core.navigation.AdminRootComponent
import com.example.gamesessionapp.data.models.navigationData.NavItem

@Composable
fun BottomNavigationBarAdmin(
    modifier: Modifier = Modifier,
    adminRootComponent: AdminRootComponent
) {
    val childStack by adminRootComponent.childStack.subscribeAsState()
    val activeChild = childStack.active.instance

    NavigationBar(
        containerColor = Color.Black,
        tonalElevation = 0.dp
    ) {
        val items = listOf(
            NavItem(
                selected = activeChild is AdminRootComponent.Child.UserManagementChild,
                onClick = { adminRootComponent.navigateToUserManagement() },
                iconRes = R.drawable.people_icon,
                label = "Пользователи"
            ),
            NavItem(
                selected = activeChild is AdminRootComponent.Child.UserSessionsChild,
                onClick = { adminRootComponent.navigateToUserSessions() },
                iconRes = R.drawable.time_icon,
                label = "Сессии"
            )
        )

        items.forEach { item ->
            NavigationBarItem(
                selected = item.selected,
                onClick = item.onClick,
                icon = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if(item.selected) Color(0xFF444444) else Color.Transparent,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .border(
                                width = 1.dp,
                                color = if(item.selected) Color.White else Color.Gray.copy(alpha = 0.3f),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = item.iconRes),
                                contentDescription = item.label,
                                tint = if (item.selected) Color.White else Color.DarkGray
                            )
                            Text(
                                text = item.label,
                                color = if(item.selected) Color.White else Color.DarkGray
                            )
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
                ),
                alwaysShowLabel = false
            )
        }
    }
}