package com.example.cat.domain.usecase

import com.example.cat.domain.CatRepository
import kotlinx.coroutines.flow.Flow

class SetFavUseCase(
    private val repository: CatRepository
) {
    suspend operator fun invoke(imageId: String): Flow<Boolean> {
        return repository.setFav(imageId)
    }
}