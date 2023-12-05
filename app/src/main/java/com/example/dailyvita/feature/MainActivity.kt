package com.example.dailyvita.feature

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.dailyvita.feature.screens.MainHostScreen
import com.example.dailyvita.ui.theme.DailyVitaTheme
import com.example.dailyvita.ui.theme.LightGreen200

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyVitaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = LightGreen200
                ) {
                    MainHostScreen()
                }
            }
        }
    }
}