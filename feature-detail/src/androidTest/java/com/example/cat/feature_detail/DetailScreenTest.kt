package com.example.cat.feature_detail

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.example.cat.domain.model.BreedsItem
import com.example.cat.domain.model.Cat
import com.example.cat.feature_detail.presentation.DetailContract
import com.example.cat.feature_detail.presentation.DetailScreen
import com.example.cat.feature_detail.presentation.DetailViewmodel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    val mockViewModel = mockk<DetailViewmodel>(relaxed = true)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        val cat = Cat(
            id = "1",
            imageUrl = "url1",
            isCatFav = false,
            breeds = listOf(BreedsItem(name = "Breed1"))
        )

        coEvery { mockViewModel.uiState } returns MutableStateFlow(
            DetailContract.State(
                cat = cat,
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
    fun showsCatCorrectly() {
        composeTestRule.setContent {
            DetailScreen(viewModel = mockViewModel)
        }
        composeTestRule.onAllNodesWithText("Breed1")
            .onFirst().assertExists()
    }


    @Test
    fun favoriteButtonClick() {
        composeTestRule.setContent {
            DetailScreen(viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithContentDescription("Favorite Icon").performClick()

        verify { mockViewModel.setEvent(DetailContract.Event.OnFavClicked("1")) }
    }

}