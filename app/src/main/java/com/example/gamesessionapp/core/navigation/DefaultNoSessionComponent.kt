package com.example.gamesessionapp.core.navigation

import com.arkivanov.decompose.ComponentContext
import com.example.gamesessionapp.data.repository.user.RoomUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

interface NoSessionComponent {
    fun checkSessionAgain()
    fun startPeriodCheck()
}

class DefaultNoSessionComponent(
    componentContext: ComponentContext,
    private val userRepository: RoomUserRepository,
    private val userLogin: String,
    private val onSessionAvailable: () -> Unit
) : NoSessionComponent, ComponentContext by componentContext {
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun checkSessionAgain() {
        coroutineScope.launch {
            val user = userRepository.getUserByLogin(userLogin)
            val currentSession = userRepository.getCurrentSessionForUser(userLogin)

            if (user?.isOnline == true && currentSession != null) {
                onSessionAvailable()
            }
        }
    }

    override fun startPeriodCheck() {
        coroutineScope.launch {
            while (true) {
                delay(5000)
                checkSessionAgain()
            }
        }
    }

}