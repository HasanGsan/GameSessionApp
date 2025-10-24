package com.example.gamesessionapp.features.admin.management.createUserForm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamesessionapp.features.admin.management.AdminManagementIntent
import com.example.gamesessionapp.features.admin.management.AdminManagementState
import com.example.gamesessionapp.features.admin.management.buttons.CreateUserButton
import com.example.gamesessionapp.features.admin.management.inputs.CreateLoginUserInput
import com.example.gamesessionapp.features.admin.management.inputs.CreatePasswordUserInput


@Composable
fun CreateUserDialog(
    modifier: Modifier = Modifier,
    state: AdminManagementState,
    onIntent: (AdminManagementIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(24.dp)
    ) {
        Text(
            text = "Создание пользователя",
            fontSize = 20.sp,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        CreateLoginUserInput(
            login = state.newUserLogin,
            onLoginChange = { login ->
                onIntent(AdminManagementIntent.EnterNewUserLogin(login))
            },
            isError = state.newUserLoginError.isNotEmpty(),
            errorMessage = state.newUserLoginError,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        CreatePasswordUserInput(
            password = state.newUserPassword,
            onPasswordChange = { password ->
                onIntent(AdminManagementIntent.EnterNewUserPassword(password))
            },
            isError = state.newUserPasswordError.isNotEmpty(),
            errorMessage = state.newUserPasswordError,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        CreateUserButton(
            onClick = {
                onIntent(AdminManagementIntent.SubmitCreateUser)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            isEnabled = state.isCreateUserFormValid
        )



    }
}