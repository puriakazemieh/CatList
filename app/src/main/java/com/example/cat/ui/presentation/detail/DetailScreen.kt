package com.example.cat.ui.presentation.detail

import android.util.Log
import androidx.compose.runtime.Composable
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailScreen(
    viewModel: DetailViewmodel = koinViewModel(),
) {
    Log.d("949494", "DetailScreen: iddddd ${viewModel.idCat}")
}