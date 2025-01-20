package com.example.cat.ui.presentation.detail

import androidx.lifecycle.SavedStateHandle
import com.example.cat.domain.model.Cat
import com.example.cat.domain.usecase.UseCase
import com.example.cat.ui.presentation.home.HomeContract
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewmodelTest {

    private lateinit var viewModel: DetailViewmodel
    private val useCase: UseCase = mockk()
    private val savedStateHandle: SavedStateHandle = mockk()
    private val testCat = Cat(id = "1", width = 200, height = 200, isCatFav = false)
    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { savedStateHandle.get<String>(DetailNavigation.ID_CAT) } returns "1"
        coEvery { useCase.getCatDetailUseCase("1") } returns flowOf(testCat)
        viewModel = DetailViewmodel(savedStateHandle, useCase)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() {
        val initialState = viewModel.createInitialState()
        assertEquals(DetailContract.State(), initialState)
    }

    @Test
    fun `getCatDetail should update state with cat details`() = runTest(testDispatcher) {
        // Act
        advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertEquals(testCat, state.cat)
        assertEquals(false, state.isLoading)
    }

    @Test
    fun `onFavClicked should update state with fav status`() = runTest(testDispatcher) {
        // Arrange
        coEvery { useCase.setFavUseCase("1") } returns flowOf(true)

        // Act
        viewModel.handleEvent(DetailContract.Event.OnFavClicked("1"))
        advanceUntilIdle()

        // Assert
        val state = viewModel.uiState.value
        assertEquals(true, state.cat?.isCatFav)
    }
}