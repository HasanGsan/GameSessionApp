package com.example.gamesessionapp.app.main

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import com.arkivanov.essenty.backhandler.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.InstanceKeeperDispatcher
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.statekeeper.StateKeeper
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import com.example.gamesessionapp.app.MainApp
import com.example.gamesessionapp.core.navigation.RootComponent
import com.example.gamesessionapp.core.navigation.DefaultRootComponent
import com.example.gamesessionapp.core.theme.GameSessionAppTheme
import com.example.gamesessionapp.data.repository.FakeNewsRepository
import com.example.gamesessionapp.features.user.weather.WeatherComponent
import com.example.gamesessionapp.features.user.news.NewsComponent
import com.example.gamesessionapp.features.user.favorites.FavoriteComponent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            GameSessionAppTheme {
                val rootComponent = rememberRootComponent()
                MainApp(rootComponent = rootComponent)
            }
        }
    }
}

@Composable
private fun rememberRootComponent() : RootComponent {

    val lifecycle = remember { LifecycleRegistry() }


    val componentContext = remember {
        DefaultComponentContext(
            lifecycle = lifecycle,
            stateKeeper = StateKeeperDispatcher(),
            instanceKeeper = InstanceKeeperDispatcher(),
        )
    }

    val newsRepository = remember { FakeNewsRepository }

    return remember(componentContext) {
        DefaultRootComponent(
            componentContext = componentContext,
            weatherComponent = { childContext ->
                WeatherComponent(
                    componentContext = childContext
                )
            },
            newsComponent = { childContext ->
                NewsComponent(
                    componentContext = childContext,
                    repository = newsRepository
                )
            },
            favoriteComponent = { childContext ->
                FavoriteComponent(
                    componentContext = childContext,
                    repository = newsRepository
                )
            }
        )
    }
}