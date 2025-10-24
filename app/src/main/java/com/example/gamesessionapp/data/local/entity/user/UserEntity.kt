package com.example.gamesessionapp.data.local.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey val login: String,
    val password: String,
    val role: UserRole,
    val isBlocked: Boolean = false,
    val isOnline: Boolean = false
)

enum class UserRole{
    USER, ADMIN
}