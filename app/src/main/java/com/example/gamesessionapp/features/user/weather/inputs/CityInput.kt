package com.example.gamesessionapp.features.user.weather.inputs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.gamesessionapp.R
import com.example.gamesessionapp.core.theme.DarkGray


@Composable
fun CityInput(
    modifier: Modifier,
    onValueChange: (String) -> Unit,
    city: String,
) {
    Column {
        OutlinedTextField(
            value = city,
            onValueChange = onValueChange,
            label = {Text(stringResource(id = R.string.input_city_name))},
            modifier = modifier
                .fillMaxWidth(),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Transparent,
                focusedLabelColor = Color(0xFF757575),
                unfocusedLabelColor = Color(0xFF757575),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color(0xFF757575),
                unfocusedBorderColor = Color.Transparent
            )
        )
        Divider(
            color = DarkGray,
            thickness = 1.dp
        )
    }
}
