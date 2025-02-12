package com.example.cat.domain.usecase

data class UseCase(
    val getCatsUseCase : GetCatsUseCase,
    val setFavUseCase : SetFavUseCase,
    val getCatDetailUseCase : GetCatDetailUseCase
)
