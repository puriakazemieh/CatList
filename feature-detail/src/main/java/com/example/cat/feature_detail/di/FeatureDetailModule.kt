package com.example.cat.feature_detail.di

import com.example.cat.feature_detail.presentation.DetailViewmodel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val featureDetailModule = module {
    viewModelOf(::DetailViewmodel)
}
