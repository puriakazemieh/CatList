package com.example.cat.ui.presentation.home

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.cat.ui.navigation.AppNavigation
import com.example.cat.ui.presentation.detail.DetailNavigation
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class HomeNavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var testNavController: TestNavHostController

    @Before
    fun setup() {
        testNavController =
            TestNavHostController(InstrumentationRegistry.getInstrumentation().targetContext).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
    }

    @Test
    fun homeNavigationRouteShouldBeCorrect() {
        // Arrange
        val homeNavigation = HomeNavigation()

        // Act
        val route = homeNavigation.route

        // Assert
        assertEquals("HomeNavigation", route)
    }


    @Test
    fun navigatesToDetailOnItemClick() = runBlocking {
        // Arrange
        composeTestRule.setContent {
            AppNavigation(testNavController)
            testNavController.navigate(HomeNavigation().route)
        }

        // Act
        composeTestRule.waitUntil(10000) {
            composeTestRule.onAllNodesWithText("Abyssinian").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onAllNodesWithText("Abyssinian")
            .onFirst()
            .performClick()

        // Assert
        val expectedRoute = DetailNavigation().routeWithArgs(DetailNavigation.ID_CAT to "abys")
        assertEquals("DetailNavigation?abys", expectedRoute)
    }

}
