package com.example.cat.feature_home

import android.annotation.SuppressLint
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.map
import com.example.cat.domain.model.Cat
import com.example.cat.domain.usecase.UseCase
import com.example.cat.feature_home.presentation.HomeContract
import com.example.cat.feature_home.presentation.HomeViewmodel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class HomeViewmodelTest {

    private lateinit var viewModel: HomeViewmodel
    private val useCase: UseCase = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setup() {
        // Set the Main dispatcher to a test dispatcher
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewmodel(useCase)
    }

    @AfterEach
    fun tearDown() {
        // Reset the Main dispatcher
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() {
        val initialState = viewModel.createInitialState()
        assertEquals(HomeContract.State(), initialState)
    }

    @SuppressLint("CheckResult")
    @Test
    fun `getCats should update catResult`() = runTest(testDispatcher) {
        // Arrange
        val testCats = listOf(
            Cat(id = "1", width = 200, height = 200),
            Cat(id = "2", width = 200, height = 200)
        )
        val testPagingData = PagingData.from(testCats)
        coEvery { useCase.getCatsUseCase() } returns flowOf(testPagingData)

        // Act
        viewModel.getCats()

        // Assert
        val catResult = viewModel.catResult.value

        val catList = mutableListOf<Cat>()
        catResult.map { cat ->
            catList.add(cat)
        }

        catList.forEachIndexed { index, cat ->
            assertEquals(testCats[index], cat)
        }

    }

    @Test
    fun `onFavClicked should update catResult with fav status`() = runTest(testDispatcher) {
        // Arrange
        coEvery { useCase.setFavUseCase("1") } returns flowOf(true)

        // Act
        viewModel.handleEvent(HomeContract.Event.OnFavClicked("1"))
        val catResult = viewModel.catResult.value
        val catList = mutableListOf<Cat>()
        catResult.map { cat -> catList.add(cat) }

        // Assert
        catList.forEachIndexed { index, cat ->
            when (index) {
                0 -> {
                    assertEquals("1", cat.id)
                    assertTrue(cat.isCatFav)
                }
            }
        }
    }

    @Test
    fun `onGoToDetail should set effect to GoToDetail`() = runTest(testDispatcher) {
        // Arrange
        val id = "1"

        // Act
        viewModel.handleEvent(HomeContract.Event.OnGoToDetail(id))

        // Assert
        val effect = viewModel.effect.take(1).first()
        assertEquals(HomeContract.Effect.GoToDetail(id), effect)
    }

    @Test
    fun `onCollectCatList should update state`() = runTest {
        val catList = mockk<LazyPagingItems<Cat>>()
        viewModel.handleEvent(HomeContract.Event.OnCollectCatList(catList))

        val state = viewModel.uiState.value
        assertEquals(catList, state.catList)
        assertEquals(false, state.isLoading)
    }

}