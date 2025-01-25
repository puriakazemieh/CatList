package com.example.cat.domain.usecase

import androidx.paging.PagingData
import com.example.cat.domain.CatRepository
import com.example.cat.domain.model.Cat
import kotlinx.coroutines.flow.Flow

class GetCatsUseCase(
    private val repository: CatRepository
) {
    suspend operator fun invoke(): Flow<PagingData<Cat>> {
        return repository.getCats()
    }
}