package com.example.cat.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.cat.data.db.CatDatabase
import com.example.cat.data.db.entity.CatEntity
import com.example.cat.data.mapper.toCat
import com.example.cat.data.remote.ApiService
import com.example.cat.data.remote.CatPagingSource
import com.example.cat.domain.CatRepository
import com.example.cat.domain.model.Cat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CatRepositoryImp(private val apiService: ApiService, private val database: CatDatabase) :
    CatRepository {

    override suspend fun getCats(): Flow<PagingData<Cat>> {
        return Pager(
            config = PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = false),
            pagingSourceFactory = {
                CatPagingSource(apiService)
            }
        ).flow.map {
            it.map {
                val isCatFav = database.catDao().getFavCat(it.id)?.isFav
                it.toCat(isCatFav == true)
            }
        }
    }

    override suspend fun getCatDetail(imageId: String): Flow<Cat> {
        return flow {
            val api = apiService.getCatDetail(imageId)
            val isCatFav = database.catDao().getFavCat(api.id)?.isFav
            emit(api.toCat(isCatFav == true))
        }
    }

    override suspend fun setFav(id: String): Flow<Boolean> {
        return flow {
            val isFav = database.catDao().getFavCat(id)?.isFav == true
            database.catDao().insertCAt(CatEntity(id = id, isFav = !isFav))
            emit(!isFav)
        }
    }


}