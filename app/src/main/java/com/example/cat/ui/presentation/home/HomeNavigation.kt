package com.example.cat.ui.presentation.home


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.cat.ui.navigation.INavigation

class HomeNavigation : INavigation {
    override val route = "HomeNavigation"
}

fun NavGraphBuilder.homeScreen(
    navController: NavHostController,
) {
    composable(route = HomeNavigation().route) {
        HomeScreen{
//            navController.navigate()
        }
    }


}