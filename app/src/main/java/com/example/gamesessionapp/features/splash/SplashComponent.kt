package com.example.gamesessionapp.features.splash

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashComponent(
    componentContext: ComponentContext,
    private val onFinished: () -> Unit,
): ComponentContext by componentContext {

    private val scope = CoroutineScope(Dispatchers.Main)

    fun start() {
        scope.launch {
            delay(1500)
            onFinished()
        }
    }
}