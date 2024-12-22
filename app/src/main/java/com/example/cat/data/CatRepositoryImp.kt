package com.example.cat.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.cat.data.remote.ApiService
import com.example.cat.data.remote.CatPagingSource
import com.example.cat.domain.CatRepository
import com.example.cat.domain.mapper.toCat
import com.example.cat.domain.model.Cat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CatRepositoryImp(private val apiService: ApiService) : CatRepository {

    override suspend fun getCats(): Flow<PagingData<Cat>> = Pager(
        config = PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = false),
        pagingSourceFactory = {
            CatPagingSource(apiService)
        }
    ).flow.map { it.map { it.toCat() } }


}