package com.example.cat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost


@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String = HomeNavigation().route
) {
    NavHost(navController = navController, startDestination = startDestination) {
        homeScreen(navController)
        detailScreen(navController)
    }
}