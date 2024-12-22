package com.example.cat.domain

import androidx.paging.PagingData
import com.example.cat.domain.model.Cat
import kotlinx.coroutines.flow.Flow

interface CatRepository {

    suspend fun getCats(): Flow<PagingData<Cat>>

    suspend fun getCatDetail(imageId: String): Flow<Cat>
}