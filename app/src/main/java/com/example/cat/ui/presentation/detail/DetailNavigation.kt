package com.example.cat.ui.presentation.detail


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.cat.ui.navigation.INavigation

class DetailNavigation : INavigation {

    override val route = "DetailNavigation?{$ID_CAT}"

    companion object {
        const val ID_CAT = "ID_CAT"
    }
}

fun NavGraphBuilder.detailScreen(
    navController: NavHostController,
) {
    composable(route = DetailNavigation().route) {
        DetailScreen()
    }


}