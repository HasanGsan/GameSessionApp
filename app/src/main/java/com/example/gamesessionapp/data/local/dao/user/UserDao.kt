package com.example.gamesessionapp.data.local.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gamesessionapp.data.local.entity.user.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: UserEntity)

    @Query("SELECT * FROM users WHERE login = :login AND password = :password LIMIT 1")
    fun getUser(login: String, password: String) : UserEntity?

    @Query("SELECT * FROM users WHERE login = :login")
    fun getUserByLogin(login: String): UserEntity?

    @Query("SELECT * FROM users WHERE role != 'ADMIN' ORDER BY login")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Query("SELECT COUNT(*) FROM users")
    fun getUserCount(): Int

    @Query("UPDATE users SET isBlocked = :isBlocked, isOnline = CASE WHEN :isBlocked THEN false ELSE isOnline END WHERE login = :login")
    fun updateBlockStatus(login: String, isBlocked: Boolean)

    @Query("UPDATE users SET isOnline = :isOnline WHERE login = :login")
    fun updateOnlineStatus(login: String, isOnline: Boolean)

}