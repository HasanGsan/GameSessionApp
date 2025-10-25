package com.example.gamesessionapp.features.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamesessionapp.data.local.entity.user.UserRole
import com.example.gamesessionapp.features.auth.button.LoginButton
import com.example.gamesessionapp.features.auth.inputs.LoginInput
import com.example.gamesessionapp.features.auth.inputs.PasswordInput
import com.example.gamesessionapp.features.auth.logo.LogoHeader

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    component: AuthComponent,
    onNavigateToUser: () -> Unit,
    onNavigateToAdmin: () -> Unit
) {

    val state by component.state.collectAsState()

    LaunchedEffect(state.authSuccess) {
        if (state.authSuccess){
            when(state.userRole){
                UserRole.USER -> onNavigateToUser()
                UserRole.ADMIN -> onNavigateToAdmin()
                null -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LogoHeader()

        Spacer(modifier = Modifier.height(32.dp))

        LoginInput(
            login = state.login,
            onLoginChange = { component.onIntent(AuthIntent.EnterLogin(it)) },
            isError = state.loginError.isNotEmpty(),
            errorMessage = state.loginError
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordInput(
            password = state.password,
            passwordVisible = state.passwordVisible,
            onPasswordChange = { component.onIntent(AuthIntent.EnterPassword(it))},
            onVisibilityChange = { component.onIntent(AuthIntent.TogglePasswordVisibility)},
            isError = state.passwordError.isNotEmpty(),
            errorMessage = state.passwordError
        )

        Spacer(modifier = Modifier.height(24.dp))

        LoginButton(
            isEnabled = state.isFormValid,
            onClick = { component.onIntent(AuthIntent.SubmitLogin) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if(state.authMessage.isNotEmpty()){
            Text(text = state.authMessage, fontSize = 14.sp)
        }
    }
}