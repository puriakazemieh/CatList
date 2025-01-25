package com.example.cat.di

import com.example.cat.data.di.dataModule
import com.example.cat.domain.di.domainModule
import com.example.cat.feature_detail.di.featureDetailModule
import com.example.cat.feature_home.di.featureHomeModule
import org.koin.dsl.module

val appModule = module {
    includes(dataModule, domainModule, featureHomeModule,featureDetailModule)
}


