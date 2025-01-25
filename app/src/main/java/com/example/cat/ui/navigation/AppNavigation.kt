package com.example.cat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.cat.feature_home.presentation.HomeNavigation
import com.example.cat.ui.presentation.detail.detailScreen
import com.example.cat.feature_home.presentation.homeScreen


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