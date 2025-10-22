package com.example.gamesessionapp.features.user.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gamesessionapp.R
import com.example.gamesessionapp.features.user.weather.buttons.RequestButton
import com.example.gamesessionapp.features.user.weather.buttons.ResultButton
import com.example.gamesessionapp.features.user.weather.cards.SearchHistoryCard
import com.example.gamesessionapp.features.user.weather.cards.WeatherCard
import com.example.gamesessionapp.features.user.weather.inputs.CityInput
import com.example.gamesessionapp.features.user.weather.inputs.DegreesInput

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    component: WeatherComponent
) {

    val state by component.state.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 10.dp)
    ) {
        Spacer(Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.input_weather_name),
            color = Color.White,
            fontSize = 24.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )

        Spacer(Modifier.height(32.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy((-12).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if(state.inputsVisible){
                Column(
                    modifier = Modifier
                ) {
                    CityInput(
                        city = state.city,
                        onValueChange = { city ->
                            component.onIntent(WeatherIntent.UpdateCity(city))
                        },
                        modifier = Modifier
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DegreesInput(
                        degrees = state.degrees,
                        onValueChange = { degrees ->
                            component.onIntent(WeatherIntent.UpdateDegrees(degrees))
                        },
                        modifier = Modifier
                    )

                }
            } else {

                WeatherCard(
                    city = state.city,
                    degrees = state.degrees,
                    weatherDescription = state.weatherDescription
                )

            }

            Spacer(Modifier.height(48.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                if(state.buttonsVisible) {
                    ResultButton(
                        isEnabled = state.isFormValid,
                        onClick = { component.onIntent(WeatherIntent.OnBlankWeatherClick) }
                    )
                }
                else {
                    RequestButton(
                       onClick = { component.onIntent(WeatherIntent.ClearAndCreateNewRequest) }
                    )
                }

            }
            Spacer(Modifier.height(48.dp))
            SearchHistoryCard(
                searchHistory = state.searchHistory,
                onHistoryItemClick = { weather ->
                    component.onIntent(WeatherIntent.UpdateCity(weather.city))
                    component.onIntent(WeatherIntent.UpdateDegrees(weather.degrees))
                }
            )
        }
    }
}

