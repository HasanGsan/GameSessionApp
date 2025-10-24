package com.example.gamesessionapp.data.local.roomConverter

import androidx.room.TypeConverter
import com.example.gamesessionapp.data.local.entity.user.UserRole

class UserRoleConverter {
    @TypeConverter
    fun fromRole(role: UserRole): String {
        return role.name
    }

    @TypeConverter
    fun toRole(value: String): UserRole {
        return UserRole.valueOf(value)
    }
}