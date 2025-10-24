package com.example.gamesessionapp.data.models.newsData

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var INSTANCE: AppDataBase? = null

    fun getDatabase(context: Context): AppDataBase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                "news_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}