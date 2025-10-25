package com.example.gamesessionapp.core.navigation

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.example.gamesessionapp.core.navigation.UserRootComponent.Child.*
import com.example.gamesessionapp.data.local.entity.session.SessionEntity
import com.example.gamesessionapp.data.repository.user.RoomUserRepository
import com.example.gamesessionapp.features.user.favorites.FavoriteComponent
import com.example.gamesessionapp.features.user.news.NewsComponent
import com.example.gamesessionapp.features.user.weather.WeatherComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

interface UserRootComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun navigateToWeather()
    fun navigateToNews()
    fun navigateToFavorite()
    fun checkSessionAndNavigate()

    sealed class Child {
        data class WeatherChild(val component: WeatherComponent) : Child()
        data class NewsChild(val component: NewsComponent) : Child()
        data class FavoriteChild(val component: FavoriteComponent) : Child()
        data class NoSessionChild(val component: NoSessionComponent) : Child()
    }
}

class DefaultUserRootComponent(
    componentContext: ComponentContext,
    private val userRepository: RoomUserRepository,
    private val userLogin: String,
    private val weatherComponent: (ComponentContext) -> WeatherComponent,
    private val newsComponent: (ComponentContext) -> NewsComponent,
    private val favoriteComponent: (ComponentContext) -> FavoriteComponent,
    private val noSessionComponent: (ComponentContext) -> NoSessionComponent
) : UserRootComponent, ComponentContext by componentContext {

    @Parcelize
    private sealed class Config : Parcelable {
        @Parcelize
        object Weather: Config()

        @Parcelize
        object News: Config()

        @Parcelize
        object Favorite: Config()

        @Parcelize
        object NoSession: Config()
    }

    private val navigation = StackNavigation<Config>()
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override val childStack: Value<ChildStack<*, UserRootComponent.Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.NoSession,
            handleBackButton = true,
            childFactory = ::createChild
        )

    init {
        checkUserSession()
    }

    private fun checkUserSession() {
        coroutineScope.launch {
            try {
                userRepository.cleanupOnlineStatues()

                val user = userRepository.getUserByLogin(userLogin)
                val isInActiveSession = userRepository.isUserInActiveSession(userLogin)

                val shouldShowContent = user?.isBlocked == false && isInActiveSession

                if (shouldShowContent) {
                    navigation.replaceCurrent(Config.Weather)
                } else {
                    if (user?.isOnline == true && !isInActiveSession) {
                        userRepository.updateOnlineStatus(userLogin, false)
                    }
                    navigation.replaceCurrent(Config.NoSession)
                }
            } catch (e: Exception) {
                navigation.replaceCurrent(Config.NoSession)
            }
        }
    }

    private fun createChild(config: Config, componentContext: ComponentContext): UserRootComponent.Child {
        return when (config) {
            is Config.Weather -> WeatherChild(weatherComponent(componentContext))
            is Config.News -> NewsChild(newsComponent(componentContext))
            is Config.Favorite -> FavoriteChild(favoriteComponent(componentContext))
            Config.NoSession -> NoSessionChild(noSessionComponent(componentContext))
        }
    }

    override fun navigateToWeather() {
        checkSessionBeforeNavigation { navigation.replaceCurrent(Config.Weather) }
    }

    override fun navigateToNews() {
        checkSessionBeforeNavigation { navigation.replaceCurrent(Config.News) }
    }

    override fun navigateToFavorite() {
        checkSessionBeforeNavigation { navigation.replaceCurrent(Config.Favorite) }
    }

    override fun checkSessionAndNavigate() {
        checkUserSession()
    }

    private fun checkSessionBeforeNavigation(onSuccess: () -> Unit) {
        coroutineScope.launch {
            val user = userRepository.getUserByLogin(userLogin)
            val isInActiveSession = userRepository.isUserInActiveSession(userLogin)

            if(user?.isOnline == true && isInActiveSession) {
                onSuccess()
            } else {
                navigation.replaceCurrent(Config.NoSession)
            }
        }
    }
}



