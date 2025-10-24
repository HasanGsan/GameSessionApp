package com.example.gamesessionapp.core.navigation

import com.arkivanov.decompose.ComponentContext

interface AdminRootComponent {

}

class DefaultsAdminRootComponent(
    componentContext: ComponentContext
) : AdminRootComponent, ComponentContext by componentContext

