package com.example.cat.domain.di

import com.example.cat.domain.CatRepository
import com.example.cat.domain.usecase.GetCatDetailUseCase
import com.example.cat.domain.usecase.GetCatsUseCase
import com.example.cat.domain.usecase.SetFavUseCase
import com.example.cat.domain.usecase.UseCase
import org.koin.dsl.module


val domainModule = module {
    single { provideCases(get()) }

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