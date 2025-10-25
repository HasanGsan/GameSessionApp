package com.example.gamesessionapp.data.local.dao.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gamesessionapp.data.local.entity.session.SessionEntity
import com.example.gamesessionapp.data.local.entity.session.UserSessionEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface SessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSession(session: SessionEntity) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserSession(userSession: UserSessionEntity) : Long

    @Query("SELECT * FROM sessions ORDER BY startTime DESC")
    fun getAllSessions(): Flow<List<SessionEntity>>

    @Query("SELECT userLogin FROM user_sessions WHERE sessionId = :sessionId")
    fun getUserLoginsForSession(sessionId: String): Flow<List<String>>

    @Query("""
    SELECT * FROM sessions 
    WHERE id IN (SELECT sessionId FROM user_sessions WHERE userLogin = :userLogin) 
    AND startTime <= :now 
    ORDER BY startTime ASC 
    LIMIT 1 
    """)
    fun getCurrentSessionForUser(userLogin: String, now: LocalDateTime): SessionEntity?

    @Query("DELETE FROM sessions WHERE id = :sessionId")
    fun deleteSession(sessionId: String) : Int

    @Query("DELETE FROM user_sessions WHERE sessionId = :sessionId")
    fun deleteUserSessionsForSession(sessionId: String) : Int
}