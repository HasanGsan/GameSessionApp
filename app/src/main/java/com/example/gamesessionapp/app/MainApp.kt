package com.example.gamesessionapp.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.example.gamesessionapp.app.bottomNavBar.BottomNavigationBar
import com.example.gamesessionapp.core.navigation.RootComponent
import com.example.gamesessionapp.features.user.favorites.FavoriteScreen
import com.example.gamesessionapp.features.user.news.NewsScreen
import com.example.gamesessionapp.features.user.weather.WeatherScreen

@Composable
fun MainApp(
    modifier: Modifier = Modifier,
    rootComponent: RootComponent
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(rootComponent = rootComponent)
        }
    )  { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Children(
                stack = rootComponent.childStack,
                animation = stackAnimation(slide()),
            ) { child ->
                when (val instance = child.instance) {
                    is RootComponent.Child.WeatherChild -> {
                        WeatherScreen(component = instance.component)
                    }
                    is RootComponent.Child.NewsChild -> {
                        NewsScreen(component = instance.component)
                    }
                    is RootComponent.Child.FavoriteChild -> {
                        FavoriteScreen(component = instance.component)
                    }
                }
            }
        }
    }
}