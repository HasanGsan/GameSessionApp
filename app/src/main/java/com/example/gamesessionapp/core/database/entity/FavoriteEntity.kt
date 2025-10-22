package com.example.gamesessionapp.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites_posts")
data class FavoriteEntity (
    @PrimaryKey
    val newsId: String,
    val addedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "read_posts")
data class ReadPostEntity (
    @PrimaryKey
    val newsId: String,
    val readAt: Long = System.currentTimeMillis()
)