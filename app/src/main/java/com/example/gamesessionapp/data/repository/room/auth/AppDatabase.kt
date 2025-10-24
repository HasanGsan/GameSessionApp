package com.example.gamesessionapp.data.repository.room.auth

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gamesessionapp.data.local.dao.user.UserDao
import com.example.gamesessionapp.data.local.entity.user.UserEntity
import com.example.gamesessionapp.data.local.roomConverter.UserRoleConverter

@Database(entities = [UserEntity::class], version = 2)
@TypeConverters(UserRoleConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "auth_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}