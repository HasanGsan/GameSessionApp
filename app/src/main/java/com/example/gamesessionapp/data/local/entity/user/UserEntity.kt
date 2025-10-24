package com.example.gamesessionapp.data.local.entity.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity (
    @PrimaryKey val login: String,
    val password: String,
    val role: UserRole
)

enum class UserRole{
    USER, ADMIN
}