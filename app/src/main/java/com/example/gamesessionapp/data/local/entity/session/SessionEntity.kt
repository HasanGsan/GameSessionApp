package com.example.gamesessionapp.data.local.entity.session

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID

@Entity(tableName = "sessions")
data class SessionEntity (
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val date: String,
    val timeRange: String,
    val startTime: LocalDateTime,
    val duration: Int,
    val createdAt: LocalDateTime = LocalDateTime.now()
)