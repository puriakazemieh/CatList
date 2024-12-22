package com.example.cat.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingState
import com.example.cat.data.remote.dto.CatDto


class CatPagingSource(private val apiService: ApiService) : PagingSource<Int, CatDto>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatDto> {
        val page = params.key ?: 0
        return try {
            val response = apiService.getCats(page = page, limit = params.loadSize)
            LoadResult.Page(
                data = response,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (response.size < params.loadSize) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CatDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}