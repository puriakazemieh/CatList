package com.example.cat.domain

import com.example.cat.domain.usecase.SetFavUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SetFavUseCaseTest {

    private lateinit var setFavUseCase: SetFavUseCase
    private val repository: CatRepository = mockk()

    @BeforeEach
    fun setUp() {
        setFavUseCase = SetFavUseCase(repository)
    }

    @Test
    fun `invoke should return true when repository sets fav successfully`() = runTest {
        // Arrange
        val imageId = "123"
        coEvery { repository.setFav(imageId) } returns flowOf(true)

        // Act
        val result = setFavUseCase(imageId).first()

        // Assert
        assertTrue(result)
    }

    @Test
    fun `invoke should return false when repository fails to set fav`() = runTest {
        // Arrange
        val imageId = "456"
        coEvery { repository.setFav(imageId) } returns flowOf(false)

        // Act
        val result = setFavUseCase(imageId).first()

        // Assert
        assertFalse(result)
    }

    @Test
    fun `invoke should return flow of false when repository fails to set favorite`() = runTest {
        // Arrange
        val imageId = "123"
        coEvery { repository.setFav(imageId) } returns flowOf(false)

        // Act
        val result = setFavUseCase(imageId)

        // Assert
        result.collect { assertEquals(false, it) }
    }
}