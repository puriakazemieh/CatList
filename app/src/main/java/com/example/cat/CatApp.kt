package com.example.cat

import android.app.Application
import com.example.cat.data.di.dataModule
import com.example.cat.di.appModule
import com.example.cat.domain.di.domainModule
import com.example.cat.feature_detail.di.featureDetailModule
import com.example.cat.feature_home.di.featureHomeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class CatApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(this@CatApp)
            modules(dataModule, domainModule, featureHomeModule,featureDetailModule)
        }
    }
}