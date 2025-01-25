package com.example.cat.ui.presentation.home


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.cat.core.ui.navigation.INavigation
import com.example.cat.ui.presentation.detail.DetailNavigation

class HomeNavigation : INavigation {
    override val route = "HomeNavigation"
}

fun NavGraphBuilder.homeScreen(
    navController: NavHostController,
) {
    composable(route = HomeNavigation().route) {
        HomeScreen{
            navController.navigate(DetailNavigation().routeWithArgs(
                DetailNavigation.ID_CAT to it
            ))
        }
    }


}