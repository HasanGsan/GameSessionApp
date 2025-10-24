package com.example.gamesessionapp.features.auth

import com.arkivanov.decompose.ComponentContext
import com.example.gamesessionapp.data.local.entity.user.UserEntity
import com.example.gamesessionapp.data.local.entity.user.UserRole
import com.example.gamesessionapp.data.repository.user.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.log

class AuthComponent(
    componentContext: ComponentContext,
    private val repository: UserRepository,
    private val onLoginSuccess: (Boolean) -> Unit
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)

    init {
        scope.launch {
            repository.initDefaultUsers()
        }
    }

    fun onIntent(intent: AuthIntent){
        when(intent) {
            is AuthIntent.EnterLogin -> updateLogin(intent.login)
            is AuthIntent.EnterPassword -> updatePassword(intent.password)
            AuthIntent.TogglePasswordVisibility -> togglePasswordVisibility()
            AuthIntent.SubmitLogin -> submitLogin()
        }
    }

    private fun updateLogin(login: String) {
        var loginError = validateLogin(login)

        _state.value = _state.value.copy(
            login = login,
            loginError = loginError
        )
        updateFormValidate()
    }




    private fun updatePassword(password: String) {
        var passwordError = validatePassword(password)

        _state.value = _state.value.copy(
            password = password,
            passwordError = passwordError
        )
        updateFormValidate()
    }



    private fun togglePasswordVisibility() {
        _state.value = _state.value.copy(
            passwordVisible = !_state.value.passwordVisible
        )
    }

    private fun updateFormValidate() {
        val loginValid = _state.value.login.isNotEmpty() && _state.value.loginError.isEmpty()
        val passwordValid = _state.value.password.isNotEmpty() && _state.value.passwordError.isEmpty()
        _state.value = _state.value.copy(
            isFormValid = loginValid && passwordValid
        )
    }

    private fun submitLogin() {
        if(!_state.value.isFormValid) return

        scope.launch {
            val user: UserEntity? = repository.login(_state.value.login, _state.value.password)
            if(user != null) {
                _state.value = _state.value.copy(
                    authSuccess = true,
                    authMessage = "Вы авторизовались как ${user.role}",
                    userRole = user.role,
                    loginError = "",
                    passwordError = ""
                )

                val isAdmin = user.role == UserRole.ADMIN
                onLoginSuccess(isAdmin)

            } else {

                val loginExists = repository.checkLoginExists(_state.value.login)

                _state.value = _state.value.copy(
                    authSuccess = false,
                    authMessage = "",
                    loginError = if (loginExists) "" else "Неверный логин",
                    passwordError = if (loginExists) "Неверный пароль" else ""
                )
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