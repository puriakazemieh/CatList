package com.example.cat.di

import com.example.cat.data.di.dataModule
import com.example.cat.domain.di.domainModule
import com.example.cat.ui.presentation.detail.DetailViewmodel
import com.example.cat.ui.presentation.home.HomeViewmodel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    includes(dataModule,domainModule)
    viewModelOf(::HomeViewmodel)
    viewModelOf(::DetailViewmodel)
}


