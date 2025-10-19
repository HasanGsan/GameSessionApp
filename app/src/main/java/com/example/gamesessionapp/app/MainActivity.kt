package com.example.gamesessionapp.app

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.gamesessionapp.core.theme.GameSessionAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setupSystemBars()
        setContent {
            GameSessionAppTheme {
//            val rootComponents = remember { RootComponents() }
//            MainScreen(rootComponents = rootComponents)
            }
        }
    }

    //Меняет цвет системных компонентов на цветовую схему приложения
    private fun setupSystemBars(){
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        window.statusBarColor = Color.BLACK
        window.navigationBarColor = Color.BLACK

        windowInsetsController.isAppearanceLightStatusBars = false
        windowInsetsController.isAppearanceLightNavigationBars = false
    }

}