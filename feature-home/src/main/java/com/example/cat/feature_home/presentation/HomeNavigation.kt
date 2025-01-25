package com.example.cat.feature_home.presentation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.cat.core.ui.navigation.INavigation

class HomeNavigation: INavigation {
    override val route = "HomeNavigation"
}

fun NavGraphBuilder.homeScreen(
    navController: NavHostController,
) {
    composable(route = HomeNavigation().route) {
        HomeScreen{
//            navController.navigate(DetailNavigation().routeWithArgs(
//                DetailNavigation.ID_CAT to it
//            ))
        }
    }


}