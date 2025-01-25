package com.example.cat.domain

import com.example.cat.domain.model.Cat
import com.example.cat.domain.usecase.GetCatDetailUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class GetCatDetailUseCaseTest {


    private val mockRepository: CatRepository = mockk()
    private val getCatDetailUseCase = GetCatDetailUseCase(mockRepository)

    @Test
    fun `invoke should return cat detail for given imageId`() = runTest {
        val imageId = "123"
        val expectedCat = Cat(id = "1", width = 200, height = 200)

        coEvery { mockRepository.getCatDetail(imageId) } returns flowOf(expectedCat)

        val result = getCatDetailUseCase(imageId)

        result.collect { cat ->
            assertEquals(expectedCat, cat)
        }
    }

    @Test
    fun `invoke should fail if cat detail does not match`() = runTest {

        val imageId = "123"
        val expectedCat = Cat(id = "123", width = 200, height = 200)
        val incorrectCat = Cat(id = "123", width = 200, height = 200)

        coEvery { mockRepository.getCatDetail(imageId) } returns flowOf(incorrectCat)

        val result = getCatDetailUseCase(imageId)

        result.collect { cat ->
            if (cat != expectedCat) {
                fail("Cat details do not match the expected values.")
            }
        }
    }
}