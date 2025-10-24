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
import com.example.gamesessionapp.core.navigation.AdminRootComponent
import com.example.gamesessionapp.core.navigation.RootComponent
import com.example.gamesessionapp.core.navigation.UserRootComponent
import com.example.gamesessionapp.features.admin.dashboard.AdminDashboardScreen
import com.example.gamesessionapp.features.auth.AuthScreen
import com.example.gamesessionapp.features.splash.SplashScreen
import com.example.gamesessionapp.features.user.favorites.FavoriteScreen
import com.example.gamesessionapp.features.user.news.NewsScreen
import com.example.gamesessionapp.features.user.weather.WeatherScreen

@Composable
fun MainApp(
    modifier: Modifier = Modifier,
    rootComponent: RootComponent
) {
        Box(modifier = modifier) {
            Children(
                stack = rootComponent.childStack,
                animation = stackAnimation(slide()),
            ) { child ->
                when (val instance = child.instance) {
                    is RootComponent.Child.SplashChild -> SplashScreen(component = instance.component)
                    is RootComponent.Child.AuthChild -> AuthScreen(
                        component = instance.component,
                        onNavigateToUser = { rootComponent.navigateToUser() },
                        onNavigateToAdmin = {rootComponent.navigateToAdmin()}
                    )
                    is RootComponent.Child.UserChild -> UserApp(component = instance.component)
                    is RootComponent.Child.AdminChild -> AdminApp(component = instance.component)

                }
            }
        }
}

@Composable
fun UserApp(component: UserRootComponent) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(userRootComponent = component)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Children(
                stack = component.childStack,
                animation = stackAnimation(slide()),
            ) { child ->
                when (val instance = child.instance) {
                    is UserRootComponent.Child.WeatherChild -> WeatherScreen(component = instance.component)
                    is UserRootComponent.Child.NewsChild -> NewsScreen(component = instance.component)
                    is UserRootComponent.Child.FavoriteChild -> FavoriteScreen(component = instance.component)
                }
            }
        }
    }
}

@Composable
fun AdminApp(component: AdminRootComponent){
    AdminDashboardScreen(component = component)
}
