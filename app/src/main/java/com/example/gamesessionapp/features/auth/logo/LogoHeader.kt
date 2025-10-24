package com.example.gamesessionapp.features.auth.logo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gamesessionapp.R

@Composable
fun LogoHeader(modifier: Modifier = Modifier) {
    Row(modifier = modifier){
        Image(
            painter = painterResource(id = R.drawable.logo_text_icon),
            contentDescription = "Лого текст",
            modifier = Modifier.size(width = 120.dp, height = 54.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.logo_icon),
            contentDescription = "Лого фото",
            modifier = Modifier.size(54.dp)
        )
    }
}