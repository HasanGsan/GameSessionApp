package com.example.gamesessionapp.core.navigation

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.example.gamesessionapp.features.admin.management.AdminManagementComponent
import com.example.gamesessionapp.features.admin.sessions.AdminSessionsComponent
import kotlinx.parcelize.Parcelize

interface AdminRootComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun navigateToUserManagement()
    fun navigateToUserSessions()

    sealed class Child {
        data class UserManagementChild(val component: AdminManagementComponent) : Child()
        data class UserSessionsChild(val component: AdminSessionsComponent) : Child()
    }
}

class DefaultAdminRootComponent(
    componentContext: ComponentContext,
    private val userManagementComponent: (ComponentContext) -> AdminManagementComponent,
    private val userSessionsComponent: (ComponentContext) -> AdminSessionsComponent
) : AdminRootComponent, ComponentContext by componentContext {

    @Parcelize
    private sealed class Config : Parcelable {
        @Parcelize
        object UserManagement : Config()

        @Parcelize
        object UserSessions : Config()
    }

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, AdminRootComponent.Child>> =
        childStack(
            source = navigation,
            initialConfiguration = Config.UserManagement,
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(config: Config, componentContext: ComponentContext) : AdminRootComponent.Child {
        return when (config){
            is Config.UserManagement -> AdminRootComponent.Child.UserManagementChild(userManagementComponent(componentContext))
            is Config.UserSessions -> AdminRootComponent.Child.UserSessionsChild(userSessionsComponent(componentContext))
        }
    }

    override fun navigateToUserManagement() {
        navigation.replaceCurrent(Config.UserManagement)
    }

    override fun navigateToUserSessions() {
        navigation.replaceCurrent(Config.UserSessions)
    }

}

