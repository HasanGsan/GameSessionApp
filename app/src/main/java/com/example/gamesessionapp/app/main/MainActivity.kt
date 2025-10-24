package com.example.gamesessionapp.app.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeperDispatcher
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import com.example.gamesessionapp.app.MainApp
import com.example.gamesessionapp.core.navigation.AdminRootComponent
import com.example.gamesessionapp.core.navigation.DefaultAdminRootComponent
import com.example.gamesessionapp.data.models.newsData.DatabaseProvider
import com.example.gamesessionapp.core.navigation.RootComponent
import com.example.gamesessionapp.core.navigation.DefaultRootComponent
import com.example.gamesessionapp.core.navigation.DefaultUserRootComponent
import com.example.gamesessionapp.core.theme.GameSessionAppTheme
import com.example.gamesessionapp.data.repository.news.FakeNewsRepository
import com.example.gamesessionapp.data.repository.room.auth.AppDatabase
import com.example.gamesessionapp.data.repository.room.news.RoomNewsRepository
import com.example.gamesessionapp.data.repository.user.UserRepository
import com.example.gamesessionapp.features.admin.management.AdminManagementComponent
import com.example.gamesessionapp.features.admin.sessions.AdminSessionsComponent
import com.example.gamesessionapp.features.auth.AuthComponent
import com.example.gamesessionapp.features.splash.SplashComponent
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
    val context = LocalContext.current


    val componentContext = remember {
        DefaultComponentContext(
            lifecycle = lifecycle,
            stateKeeper = StateKeeperDispatcher(),
            instanceKeeper = InstanceKeeperDispatcher(),
        )
    }

    val newsRepository = remember {
        val database = DatabaseProvider.getDatabase(context)
        val dao = database.newsStatusDao()
        RoomNewsRepository(
            newsStatusDao = dao,
            fakeNewsRepository = FakeNewsRepository
        )
    }

    val userRepository = remember {
        val database = AppDatabase.getDatabase(context)
        val userDao = database.userDao()
        UserRepository(userDao)
    }

    return remember(componentContext) {
        DefaultRootComponent(
            componentContext = componentContext,
            splashComponent = { childContext, onFinished ->
                SplashComponent(
                    componentContext = childContext,
                    onFinished = onFinished
                )
            },
            authComponent = { childContext, onLoginSuccess ->
               AuthComponent(
                   componentContext = childContext,
                   repository = userRepository,
                   onLoginSuccess = onLoginSuccess
               )
            },
            adminComponent = { childContext ->
                DefaultAdminRootComponent(
                    componentContext = childContext,
                    userManagementComponent = { managementChildContext ->
                        AdminManagementComponent(managementChildContext)
                    },
                    userSessionsComponent = { sessionsChildContext ->
                        AdminSessionsComponent(sessionsChildContext)
                    }
                )
            },
            userRootComponent = { childContext ->
                DefaultUserRootComponent(
                    componentContext = childContext,
                    weatherComponent = { weatherChildContext ->
                        WeatherComponent(componentContext = weatherChildContext)
                    },
                    newsComponent = { newsChildContext ->
                        NewsComponent(
                            componentContext = newsChildContext,
                            repository = newsRepository
                        )
                    },
                    favoriteComponent = { favoriteChildContext ->
                        FavoriteComponent(
                            componentContext = favoriteChildContext,
                            repository = newsRepository
                        )
                    }
                )
            }
        )
    }
}