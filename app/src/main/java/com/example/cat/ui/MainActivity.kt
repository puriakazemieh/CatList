package com.example.cat.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.cat.ui.navigation.HomeNavigation
import com.example.cat.ui.navigation.AppNavigation
import com.example.cat.ui.theme.CatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatTheme {
                val navController = rememberNavController()
                AppNavigation(
                    navController = navController,
                    startDestination = HomeNavigation().route
                )
            }
        }
    }
}
