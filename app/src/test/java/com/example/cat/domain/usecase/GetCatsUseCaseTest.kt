package com.example.cat.domain.usecase

import android.annotation.SuppressLint
import androidx.paging.PagingData
import androidx.paging.map
import com.example.cat.domain.CatRepository
import com.example.cat.domain.model.Cat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GetCatsUseCaseTest {

    private val mockRepository: CatRepository = mockk()
    private val getCatsUseCase = GetCatsUseCase(mockRepository)

    @SuppressLint("CheckResult")
    @Test
    fun `invoke should return paging data of cats`() = runTest {

        val expectedCats = listOf(
            Cat(id = "1", width = 200, height = 200),
            Cat(id = "2", width = 200, height = 200)
        )
        val pagingData = PagingData.from(expectedCats)

        coEvery { mockRepository.getCats() } returns flowOf(pagingData)

        val result: Flow<PagingData<Cat>> = getCatsUseCase()

        result.collect { pagingDataResult ->
            val catList = mutableListOf<Cat>()
            pagingDataResult.map { cat ->
                catList.add(cat)
            }
            catList.forEachIndexed { index, cat ->
                assertEquals(expectedCats[index], cat)
            }
        }

    }

    @Test
    fun `invoke should throw exception when repository throws exception`() = runTest {
        // Arrange
        val exception = Exception("API call failed")
        coEvery { mockRepository.getCats() } returns flow { throw exception }

        // Act & Assert
        assertThrows(Exception::class.java) {
            runTest {
                getCatsUseCase()
            }
        }
    }


}
