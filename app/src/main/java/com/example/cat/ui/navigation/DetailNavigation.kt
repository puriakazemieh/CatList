package com.example.cat.ui.navigation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.cat.core.constant.ID_CAT
import com.example.cat.core.ui.navigation.INavigation
import com.example.cat.feature_detail.presentation.DetailScreen

class DetailNavigation : INavigation {
    override val route = "DetailNavigation?{$ID_CAT}"

}

fun NavGraphBuilder.detailScreen(
    navController: NavHostController,
) {
    composable(route = DetailNavigation().route) {
        DetailScreen()
    }


}