package com.example.gamesessionapp.app.bottomNavBar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.example.gamesessionapp.R
import com.example.gamesessionapp.core.navigation.RootComponent
import com.example.gamesessionapp.core.navigation.UserRootComponent
import com.example.gamesessionapp.data.models.navigationData.NavItem

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    userRootComponent: UserRootComponent
) {
    val childStack by userRootComponent.childStack.subscribeAsState()
    val activeChild = childStack.active.instance

    NavigationBar(
        containerColor = Color.Black,
        tonalElevation = 0.dp
    ) {
        val items = listOf(
            NavItem(
                selected = activeChild is UserRootComponent.Child.WeatherChild,
                onClick = { userRootComponent.navigateToWeather() },
                iconRes = R.drawable.weather_navigation_icon,
                label = "Погода"
            ),
            NavItem(
                selected = activeChild is UserRootComponent.Child.NewsChild,
                onClick = { userRootComponent.navigateToNews() },
                iconRes = R.drawable.news_navigation_icon,
                label = "Новости"
            ),
            NavItem(
                selected = activeChild is UserRootComponent.Child.FavoriteChild,
                onClick = { userRootComponent.navigateToFavorite() },
                iconRes = R.drawable.favorite_navigation_icon,
                label = "Избранное"
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