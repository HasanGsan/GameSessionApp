package com.example.gamesessionapp.core.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.example.gamesessionapp.features.user.favorites.FavoriteComponent
import com.example.gamesessionapp.features.user.news.NewsComponent
import com.example.gamesessionapp.features.user.weather.WeatherComponent
import android.os.Parcelable
import com.example.gamesessionapp.features.auth.AuthComponent
import com.example.gamesessionapp.features.splash.SplashComponent
import kotlinx.parcelize.Parcelize

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun navigateToSplash()
    fun navigateToAuth()
    fun navigateToAdmin()
    fun navigateToUser(login: String)

    sealed class Child {
        data class SplashChild(val component: SplashComponent) : Child()
        data class AuthChild(val component: AuthComponent) : Child()
        data class UserChild(val component: UserRootComponent) : Child()
        data class AdminChild(val component: AdminRootComponent) : Child()
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val splashComponent: (ComponentContext, onFinished: () -> Unit) -> SplashComponent,
    private val authComponent: (ComponentContext, onLoginSuccess: (String, Boolean) -> Unit) -> AuthComponent,
    private val adminComponent: (ComponentContext) -> AdminRootComponent,
    private val userRootComponent: (ComponentContext, String) -> UserRootComponent
) : RootComponent, ComponentContext by componentContext {

    @Parcelize
    private sealed class Config : Parcelable {

        @Parcelize
        object Splash : Config()

        @Parcelize
        object Auth : Config()

        @Parcelize
        object User: Config()

        @Parcelize
        object Admin : Config()

    }

    private val navigation = StackNavigation<Config>()
    private var currentUserLogin: String = ""

    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.Splash,
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(config: Config, componentContext: ComponentContext): RootComponent.Child {
        return when (config) {
            is Config.Splash -> RootComponent.Child.SplashChild(splashComponent(componentContext) { navigateToAuth() })
            is Config.Auth -> RootComponent.Child.AuthChild(authComponent(componentContext) { login, isAdmin ->
                if(isAdmin) navigateToAdmin() else navigateToUser(login)
            })
            is Config.User -> RootComponent.Child.UserChild(userRootComponent(componentContext, currentUserLogin))
            is Config.Admin -> RootComponent.Child.AdminChild(adminComponent(componentContext))
        }
    }

    override fun navigateToSplash() {
        navigation.replaceCurrent(Config.Splash)
    }

    override fun navigateToAuth() {
        navigation.replaceCurrent(Config.Auth)
    }

    override fun navigateToAdmin() {
        navigation.replaceCurrent(Config.Admin)
    }

    override fun navigateToUser(login: String) {
        currentUserLogin = login
        navigation.replaceCurrent(Config.User)
    }

}