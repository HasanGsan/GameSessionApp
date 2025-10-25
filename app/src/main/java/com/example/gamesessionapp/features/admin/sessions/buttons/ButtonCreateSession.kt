package com.example.gamesessionapp.features.admin.sessions.buttons

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonCreateSession(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = false,
    onClick: () -> Unit = {},
) {
    var isPressed by remember { mutableStateOf(false) }
    Button(
        onClick = {
            onClick()
            isPressed = false
        },
        enabled = isEnabled,
        modifier = modifier
            .height(54.dp)
            .width(124.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isEnabled){ if(isPressed) Color.LightGray else Color.White } else { Color.Gray },
            disabledContainerColor = Color.Gray,
        )
    ) {
        Text(text = "СОЗДАТЬ", fontSize = 16.sp, color = Color.DarkGray)
    }
}