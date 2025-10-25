package com.example.gamesessionapp.data.repository.user

import com.example.gamesessionapp.data.local.dao.user.SessionDao
import com.example.gamesessionapp.data.local.dao.user.UserDao
import com.example.gamesessionapp.data.local.entity.session.SessionEntity
import com.example.gamesessionapp.data.local.entity.session.UserSessionEntity
import com.example.gamesessionapp.data.local.entity.user.UserEntity
import com.example.gamesessionapp.data.local.entity.user.UserRole
import com.example.gamesessionapp.features.admin.sessions.SessionWithUsers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

class RoomUserRepository(private val userDao: UserDao, private val sessionDao: SessionDao) {

    suspend fun addUser(user: UserEntity) = withContext(Dispatchers.IO){
        userDao.insertUser(user)
    }

    suspend fun login(login: String, password: String) : UserEntity? = withContext(Dispatchers.IO) {
        userDao.getUser(login, password)
    }

    suspend fun checkLoginExists(login: String): Boolean = withContext(Dispatchers.IO){
        userDao.getUserByLogin(login) != null
    }

    suspend fun updateBlockStatus(login: String, isBlocked: Boolean) = withContext(Dispatchers.IO) {
        userDao.updateBlockStatus(login, isBlocked)

        if(isBlocked){
            userDao.updateOnlineStatus(login, false)
        }
    }

    suspend fun updateOnlineStatus(login: String, isOnline: Boolean) = withContext(Dispatchers.IO) {
        userDao.updateOnlineStatus(login, isOnline)
    }

     fun getAllUsers(): Flow<List<UserEntity>> {
        return userDao.getAllUsers()
    }

    suspend fun initDefaultUsers() {
        if(userDao.getUserCount() == 0) {
            addUser(UserEntity("Логин_Админа", "1234pass", UserRole.ADMIN))
            addUser(UserEntity("Логин_Юзера", "1234pass", UserRole.USER))
            addUser(UserEntity("Юзер_Один", "1234pass", UserRole.USER))
            addUser(UserEntity("Юзер_Два", "1234pass", UserRole.USER))
        }
    }

    suspend fun createSession(
        date: String,
        timeRange: String,
        startTime: LocalDateTime,
        duration: Int,
        userLogins: List<String>
    ) = withContext(Dispatchers.IO) {
        val session = SessionEntity(
            date = date,
            timeRange = timeRange,
            startTime = startTime,
            duration = duration
        )

        sessionDao.insertSession(session)

        userLogins.forEach { login ->
            sessionDao.insertUserSession(UserSessionEntity(session.id, login))
            userDao.updateOnlineStatus(login, true)
        }
    }

    suspend fun isUserInActiveSession(login: String): Boolean = withContext(Dispatchers.IO) {
        val session = getCurrentSessionForUser(login)
        session != null && isSessionActive(session)
    }

    private fun isSessionActive(session: SessionEntity): Boolean {
        val now = LocalDateTime.now()
        val sessionEnd = session.startTime.plusHours(session.duration.toLong())
        return now.isAfter(session.startTime) && now.isBefore(sessionEnd)
    }

    suspend fun getCurrentSessionForUser(login: String): SessionEntity? = withContext(Dispatchers.IO) {
        val now = LocalDateTime.now()
        val session = sessionDao.getCurrentSessionForUser(login, now)

        if (session != null && isSessionActive(session)) {
            session
        } else {
            val user = userDao.getUserByLogin(login)
            if (user?.isOnline == true) {
                userDao.updateOnlineStatus(login, false)
            }
            null
        }
    }

    fun getAllSessionsWithUser(): Flow<List<SessionWithUsers>> {
        return sessionDao.getAllSessions().map { sessions ->
            sessions.map { session ->
                val users = runBlocking { getUserForSession(session.id) }
                SessionWithUsers(session, users)
            }
        }
    }

    suspend fun getUserForSession(sessionId: String): List<UserEntity> = withContext(Dispatchers.IO) {
        try {
            val userLogins = sessionDao.getUserLoginsForSession(sessionId).first()
            userLogins.mapNotNull { login -> userDao.getUserByLogin(login) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getNonAdminUsers(): List<UserEntity> = withContext(Dispatchers.IO) {
        userDao.getAllUsers().first().filter { it.role != UserRole.ADMIN && !it.isBlocked }
    }

    suspend fun getAvailableUsersForSession(): List<UserEntity> = withContext(Dispatchers.IO) {
        userDao.getAllUsers().first().filter {
            it.role != UserRole.ADMIN && !it.isBlocked && !it.isOnline
        }
    }

    suspend fun getUserByLogin(login: String): UserEntity? = withContext(Dispatchers.IO) {
        userDao.getUserByLogin(login)
    }

    suspend fun  cleanupOnlineStatues() = withContext(Dispatchers.IO) {
        val allUsers = userDao.getAllUsers().first()
        val now = LocalDateTime.now()

        allUsers.forEach { user ->
            if(user.isOnline) {
                val currentSession = getCurrentSessionForUser(user.login)

                if(currentSession == null) {
                    userDao.updateOnlineStatus(user.login, false)
                }
            }
        }
    }

    init {
        CoroutineScope(Dispatchers.IO).launch {
            cleanupOnlineStatues()
        }
    }

}
