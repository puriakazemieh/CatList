package com.example.cat.ui.navigation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.cat.core.constant.ID_CAT
import com.example.cat.core.ui.navigation.INavigation
import com.example.cat.feature_home.presentation.HomeScreen

class HomeNavigation: INavigation {
    override val route = "HomeNavigation"
}

fun NavGraphBuilder.homeScreen(
    navController: NavHostController,
) {
    composable(route = HomeNavigation().route) {
        HomeScreen {
            navController.navigate(DetailNavigation().routeWithArgs(
                ID_CAT to it
            ))
        }
    }


}