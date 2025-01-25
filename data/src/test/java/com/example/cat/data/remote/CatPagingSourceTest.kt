package com.example.cat.data.remote

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cat.data.remote.dto.CatDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CatPagingSourceTest {

    private val mockApiService = mockk<ApiService>()

    @Test
    fun `CatPagingSource should return data from API`() = runTest {
        // Arrange
        val mockApiResponse = listOf(
            CatDto(id = "1", width = 200, height = 200, url = "url1"),
            CatDto(id = "2", width = 300, height = 300, url = "url2")
        )
        coEvery { mockApiService.getCats(page = 0, limit = 10) } returns mockApiResponse
        val pagingSource = CatPagingSource(mockApiService)

        // Act
        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        // Assert
        assertTrue(loadResult is PagingSource.LoadResult.Page)
        val pageResult = loadResult as PagingSource.LoadResult.Page
        assertEquals(mockApiResponse, pageResult.data)
    }


    @Test
    fun `CatPagingSource should return Error`() = runBlocking {

        val exception = RuntimeException("Network Error")
        coEvery { mockApiService.getCats(page = 0, limit = 10) } throws exception

        val pagingSource = CatPagingSource(mockApiService)

        val loadResult = pagingSource.load(PagingSource.LoadParams.Refresh(0, 10, false))

        assertTrue(loadResult is PagingSource.LoadResult.Error)
        val error = loadResult as PagingSource.LoadResult.Error
        assertEquals(exception, error.throwable)
    }

    @Test
    fun `CatPagingSource should return RefreshKey and prev and next key`() {

        val firstPage = PagingSource.LoadResult.Page(
            data = listOf(CatDto(id = "1", width = 200, height = 200, url = "url1")),
            prevKey = null,
            nextKey = 1
        )

        val pagingState = PagingState(
            pages = listOf(firstPage),
            anchorPosition = 10,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        val pagingSource = CatPagingSource(mockApiService)

        val refreshKey = pagingSource.getRefreshKey(pagingState)
        assertEquals(0, refreshKey)
        assertNull(firstPage.prevKey)
        assertEquals(1, firstPage.nextKey)
    }
}