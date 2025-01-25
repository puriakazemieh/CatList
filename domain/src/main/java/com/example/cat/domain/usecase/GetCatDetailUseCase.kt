package com.example.cat.domain.usecase

import com.example.cat.domain.CatRepository
import com.example.cat.domain.model.Cat
import kotlinx.coroutines.flow.Flow

class GetCatDetailUseCase(
    private val repository: CatRepository
) {
    suspend operator fun invoke(imageId: String): Flow<Cat> {
        return repository.getCatDetail(imageId)
    }
}