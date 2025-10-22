package com.example.gamesessionapp.core.database.newsData

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gamesessionapp.data.local.dao.NewsStatusDao
import com.example.gamesessionapp.data.local.entity.FavoriteEntity
import com.example.gamesessionapp.data.local.entity.ReadPostEntity

@Database(
    entities = [FavoriteEntity::class, ReadPostEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDataBase : RoomDatabase() {
    abstract fun newsStatusDao(): NewsStatusDao
}