package com.example.gamesessionapp.core.navigation

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.example.gamesessionapp.features.user.favorites.FavoriteComponent
import com.example.gamesessionapp.features.user.news.NewsComponent
import com.example.gamesessionapp.features.user.weather.WeatherComponent
import kotlinx.parcelize.Parcelize

interface UserRootComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun navigateToWeather()
    fun navigateToNews()
    fun navigateToFavorite()

    sealed class Child {
        data class WeatherChild(val component: WeatherComponent) : Child()
        data class NewsChild(val component: NewsComponent) : Child()
        data class FavoriteChild(val component: FavoriteComponent) : Child()
    }
}

class DefaultUserRootComponent(
    componentContext: ComponentContext,
    private val weatherComponent: (ComponentContext) -> WeatherComponent,
    private val newsComponent: (ComponentContext) -> NewsComponent,
    private val favoriteComponent: (ComponentContext) -> FavoriteComponent,
) : UserRootComponent, ComponentContext by componentContext {

    @Parcelize
    private sealed class Config : Parcelable {
        @Parcelize
        object Weather: Config()

        @Parcelize
        object News: Config()

        @Parcelize
        object Favorite: Config()
    }

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, UserRootComponent.Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.Weather,
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(config: Config, componentContext: ComponentContext): UserRootComponent.Child {
        return when (config) {
            is Config.Weather -> UserRootComponent.Child.WeatherChild(weatherComponent(componentContext))
            is Config.News -> UserRootComponent.Child.NewsChild(newsComponent(componentContext))
            is Config.Favorite -> UserRootComponent.Child.FavoriteChild(favoriteComponent(componentContext))
        }
    }

    override fun navigateToWeather() {
        navigation.replaceCurrent(Config.Weather)
    }

    override fun navigateToNews() {
        navigation.replaceCurrent(Config.News)
    }

    override fun navigateToFavorite() {
        navigation.replaceCurrent(Config.Favorite)
    }

}