package com.example.gamesessionapp.features.admin.sessions

import com.example.gamesessionapp.data.local.entity.session.SessionEntity
import com.example.gamesessionapp.data.local.entity.user.UserEntity

data class AdminSessionsState (
    val sessions: List<SessionWithUsers> = emptyList(),
    val showCreateSessionDialog: Boolean = false,
    val availableUsers: List<UserEntity> = emptyList(),
    val selectedUsers: Set<String> = emptySet(),
    val date: String = "",
    val time: String = "",
    val duration: String = "",
    val isFormValid: Boolean = false
)

data class SessionWithUsers(
    val session: SessionEntity,
    val users: List<UserEntity>
)