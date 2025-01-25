package com.example.cat.ui.presentation.home

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.example.cat.domain.model.BreedsItem
import com.example.cat.domain.model.Cat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    val mockViewModel = mockk<HomeViewmodel>(relaxed = true)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        val catList = listOf(
            Cat(
                id = "1",
                imageUrl = "url1",
                isCatFav = false,
                breeds = listOf(BreedsItem(name = "Breed1"))
            ),
            Cat(
                id = "2",
                imageUrl = "url2",
                isCatFav = true,
                breeds = listOf(BreedsItem(name = "Breed2"))
            )
        )

        val catPagingData = PagingData.from(catList)
        val mockStateFlow = MutableStateFlow(catPagingData)

        coEvery { mockViewModel.catResult } returns mockStateFlow

        val lazyPagingItems = mockk<LazyPagingItems<Cat>>(relaxed = true)
        coEvery { lazyPagingItems.itemCount } returns 2
        coEvery { lazyPagingItems[0] } returns catList[0]
        coEvery { lazyPagingItems[1] } returns catList[1]

        coEvery { mockViewModel.uiState } returns MutableStateFlow(
            HomeContract.State(
                catList = lazyPagingItems,
                isLoading = false
            )
        )
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.mainClock.advanceTimeBy(5000)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun showsCatListCorrectly() = runTest(testDispatcher) {
        composeTestRule.setContent {
            HomeScreen(viewModel = mockViewModel, gotoDetail = {})
        }
        // Check if the list displays items
        composeTestRule.onNodeWithText("Breed1").assertExists()
        composeTestRule.onNodeWithText("Breed2").assertExists()
    }

    @Test
    fun navigatesToDetailOnItemClick() {
        var navigatedToDetailId: String? = null

        composeTestRule.setContent {
            HomeScreen(viewModel = mockViewModel, gotoDetail = { id -> navigatedToDetailId = id })
        }

        composeTestRule.onNodeWithText("Breed1").assertExists()
        composeTestRule.onNodeWithText("Breed1").performClick()

        verify { mockViewModel.setEvent(HomeContract.Event.OnGoToDetail("1")) }
    }

    @Test
    fun favoriteButtonClick() {

        composeTestRule.setContent {
            HomeScreen(viewModel = mockViewModel, gotoDetail = {})
        }

        composeTestRule.onAllNodesWithContentDescription("Favorite Icon")
            .onFirst()
            .performClick()

        verify { mockViewModel.setEvent(HomeContract.Event.OnFavClicked("1")) }
    }

}

