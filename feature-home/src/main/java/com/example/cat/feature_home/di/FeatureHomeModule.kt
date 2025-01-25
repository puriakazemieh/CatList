package com.example.cat.feature_home.di

import com.example.cat.feature_home.presentation.HomeViewmodel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val featureHomeModule = module {
    viewModelOf(::HomeViewmodel)
}
