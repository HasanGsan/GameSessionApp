package com.example.gamesessionapp.data.repository.user

import com.example.gamesessionapp.data.local.dao.user.UserDao
import com.example.gamesessionapp.data.local.entity.user.UserEntity
import com.example.gamesessionapp.data.local.entity.user.UserRole
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RoomUserRepository(private val userDao: UserDao) {

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
    }

    suspend fun updateOnlineStatus(login: String, isOnline: Boolean) = withContext(Dispatchers.IO) {
        userDao.updateOnlineStatus(login, isOnline)
    }

     fun getAllUsers(): Flow<List<UserEntity>> {
        return userDao.getAllUsers()
    }

    suspend fun initDefaultUsers() {
        if(userDao.getUserCount() == 0) {
            addUser(UserEntity("Логин_Юзера", "1234pass", UserRole.USER))
            addUser(UserEntity("Логин_Админа", "1234pass", UserRole.ADMIN))
        }
    }

}