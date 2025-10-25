package com.example.gamesessionapp.data.local.entity.session

import androidx.room.Entity

@Entity(tableName = "user_sessions", primaryKeys = ["sessionId", "userLogin"])
data class UserSessionEntity (
    val sessionId: String,
    val userLogin: String
)