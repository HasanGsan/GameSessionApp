package com.example.gamesessionapp.data.models.newsData

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.gamesessionapp.data.local.dao.news.NewsStatusDao
import com.example.gamesessionapp.data.local.entity.favorite.FavoriteEntity
import com.example.gamesessionapp.data.local.entity.favorite.ReadPostEntity

@Database(
    entities = [FavoriteEntity::class, ReadPostEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDataBase : RoomDatabase() {
    abstract fun newsStatusDao(): NewsStatusDao
}