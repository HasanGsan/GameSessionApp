package com.example.gamesessionapp.data.repository.room.adminSession

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gamesessionapp.data.local.dao.user.SessionDao
import com.example.gamesessionapp.data.local.entity.session.SessionEntity
import com.example.gamesessionapp.data.local.entity.session.UserSessionEntity
import com.example.gamesessionapp.data.local.roomConverter.LocalDateTimeConverter

@Database(
    entities = [SessionEntity::class, UserSessionEntity::class],
    version = 1
)

@TypeConverters(LocalDateTimeConverter::class)
abstract class AppSessionDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao

    companion object {
        @Volatile
        private var INSTANCE: AppSessionDatabase? = null

        fun getDatabase(context: Context) : AppSessionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppSessionDatabase::class.java,
                    "session_database").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                    instance
            }
        }
    }
}