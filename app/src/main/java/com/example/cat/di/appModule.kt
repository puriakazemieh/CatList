package com.example.cat.di

import com.example.cat.data.CatRepositoryImp
import com.example.cat.data.di.dataModule
import com.example.cat.domain.CatRepository
import com.example.cat.domain.usecase.GetCatDetailUseCase
import com.example.cat.domain.usecase.GetCatsUseCase
import com.example.cat.domain.usecase.SetFavUseCase
import com.example.cat.domain.usecase.UseCase
import com.example.cat.ui.presentation.detail.DetailViewmodel
import com.example.cat.ui.presentation.home.HomeViewmodel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {
    includes(dataModule)
    singleOf(::CatRepositoryImp) { bind<CatRepository>() }
    single { provideCases(get()) }
    viewModelOf(::HomeViewmodel)
    viewModelOf(::DetailViewmodel)

}


fun provideCases(
    repository: CatRepository,
): UseCase {
    return UseCase(
        getCatsUseCase = GetCatsUseCase(repository),
        getCatDetailUseCase = GetCatDetailUseCase(repository),
        setFavUseCase = SetFavUseCase(repository),
    )
}
