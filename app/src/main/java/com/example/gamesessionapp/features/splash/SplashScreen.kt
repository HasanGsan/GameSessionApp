package com.example.gamesessionapp.features.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.gamesessionapp.R
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    component: SplashComponent,
    size: Dp = 48.dp,
    color: Color = Color.White,
    segmentsCount: Int = 12
) {

    LaunchedEffect(Unit) {
        component.start()
    }

    val transition = rememberInfiniteTransition(label = "spinner")
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "progress"
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.logo_text_icon),
                contentDescription = "Лого текст",
                modifier = Modifier.size(width = 120.dp, height = 54.dp)
            )
            Spacer(modifier = Modifier.width(3.dp))
            Canvas(modifier = modifier.size(size)) {
                val centerX = this.size.width / 2
                val centerY = this.size.height / 2
                val radius = this.size.minDimension / 2
                val angleStep = 360f / segmentsCount

                for (i in 0 until segmentsCount) {
                    val phase = (progress * segmentsCount - i).mod(segmentsCount.toFloat())
                    val alpha = 0.2f + 0.8f * (1f - phase / segmentsCount)
                    val segmentColor = color.copy(alpha = alpha.coerceIn(0f, 1f))

                    val angle = (i * angleStep) * (PI / 180.0)

                    val start = Offset(
                        x = centerX + (radius * 0.65f) * cos(angle).toFloat(),
                        y = centerY + (radius * 0.65f) * sin(angle).toFloat()
                    )
                    val end = Offset(
                        x = centerX + (radius * 0.85f) * cos(angle).toFloat(),
                        y = centerY + (radius * 0.85f) * sin(angle).toFloat()
                    )
                    drawLine(
                        color = segmentColor,
                        start = start,
                        end = end,
                        strokeWidth = radius * 0.20f,
                        cap = StrokeCap.Round
                    )
                }
            }
        }
    }
}