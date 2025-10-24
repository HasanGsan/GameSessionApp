package com.example.gamesessionapp.data.local.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gamesessionapp.data.local.entity.user.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE login = :login AND password = :password LIMIT 1")
    fun getUser(login: String, password: String) : UserEntity?

    @Query("SELECT * FROM users WHERE login = :login")
    fun getUserByLogin(login: String): UserEntity?
}