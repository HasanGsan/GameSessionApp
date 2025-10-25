package com.example.gamesessionapp.features.admin.management

import com.arkivanov.decompose.ComponentContext
import com.example.gamesessionapp.data.local.entity.user.UserEntity
import com.example.gamesessionapp.data.local.entity.user.UserRole
import com.example.gamesessionapp.data.repository.user.RoomUserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class AdminManagementComponent(
    componentContext: ComponentContext,
    private val roomUserRepository: RoomUserRepository
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(AdminManagementState())
    val state: StateFlow<AdminManagementState> = _state.asStateFlow()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        loadUsers()
    }

    private fun loadUsers() {
        coroutineScope.launch {
            roomUserRepository.getAllUsers().collect { users ->
                _state.value = _state.value.copy(users = users)
            }
        }
    }

    fun onIntent(intent: AdminManagementIntent) {
        when (intent) {
            is AdminManagementIntent.EnterNewUserLogin -> updateNewUserLogin(intent.login)
            is AdminManagementIntent.EnterNewUserPassword -> updateNewUserPassword(intent.password)
            is AdminManagementIntent.OpenCreateUserDialog -> openCreateUserDialog()
            is AdminManagementIntent.CloseCreateUserDialog -> closeCreateUserDialog()
            is AdminManagementIntent.SubmitCreateUser -> submitCreateUser()
            is AdminManagementIntent.BlockUser -> blockUser(intent.login)
            is AdminManagementIntent.UnblockUser -> unblockUser(intent.login)
        }
    }

    private fun blockUser(login: String) {
        coroutineScope.launch {
            try {
                roomUserRepository.updateBlockStatus(login, true)
            } catch (e: kotlin.Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun unblockUser(login: String) {
        coroutineScope.launch {
            try {
                roomUserRepository.updateBlockStatus(login, false)
            } catch (e: kotlin.Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun updateNewUserLogin(login: String){
        val loginError = validateLogin(login)
        _state.value = _state.value.copy(
            newUserLogin = login,
            newUserLoginError = loginError
        )
        updateCreateNewUserFormValidate()
    }

    private fun updateNewUserPassword(password: String){
        val passwordError = validatePassword(password)
        _state.value = _state.value.copy(
            newUserPassword = password,
            newUserPasswordError = passwordError
        )
        updateCreateNewUserFormValidate()
    }

    private fun openCreateUserDialog(){
        _state.value = _state.value.copy(
            showCreateUserDialog = true
        )
    }

    private fun closeCreateUserDialog(){
        _state.value = _state.value.copy(
            showCreateUserDialog = false,
            newUserLogin = "",
            newUserPassword = "",
            newUserLoginError = "",
            newUserPasswordError = ""
        )
    }

    private fun updateCreateNewUserFormValidate() {
        val loginValid = _state.value.newUserLogin.isNotEmpty() && _state.value.newUserLoginError.isEmpty()
        val passwordValid = _state.value.newUserPassword.isNotEmpty() && _state.value.newUserPasswordError.isEmpty()

        _state.value = _state.value.copy(
            isCreateUserFormValid = loginValid && passwordValid
        )
    }

    private fun submitCreateUser() {
        coroutineScope.launch {
            try {
                val loginExists = roomUserRepository.checkLoginExists(_state.value.newUserLogin)

                if(loginExists) {
                    _state.value = _state.value.copy(
                        newUserLoginError = "Пользователь с таким логином уже существует"
                    )
                    return@launch
                }

                val newUser = UserEntity(
                    login = _state.value.newUserLogin,
                    password = _state.value.newUserPassword,
                    role = UserRole.USER,
                    isBlocked = false,
                    isOnline = false
                )

                roomUserRepository.addUser(newUser)
                closeCreateUserDialog()
                loadUsers()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun validateLogin(login: String): String {
        if(login.isEmpty()) return "Поле не может быть пустым"
        if(!login.matches(Regex("[а-яёА-ЯЁ_]+"))) return "Логин должен быть на кириллице"
        return ""
    }

    private fun validatePassword(password: String): String {
        if(password.isEmpty()) return "Поле не может быть пустым"
        if(password.length < 6) return "Пароль должен содержать не менее 6 символов"
        if(password.length > 12) return "Пароль должен содержать максимум 12 символов"
        if(!password.matches(Regex(".*\\d.*"))) return "Пароль должен содержать хотя бы одну цифру"
        if(!password.matches(Regex(".*[a-zA-Z].*"))) return "Пароль должен содержать хотя бы одну латинскую букву"
        if(password.matches(Regex(".*[а-яёА-ЯЁ].*"))) return "Пароль должен содержать только цифры и латинские буквы"
        return ""
    }

}