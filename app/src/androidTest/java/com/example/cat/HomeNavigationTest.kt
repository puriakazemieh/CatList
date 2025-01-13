package com.example.cat

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.cat.ui.presentation.home.HomeNavigation
import junit.framework.TestCase.assertEquals
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
    fun `homeNavigationrouteshouldbecorrect`() {
        // Arrange
        val homeNavigation = HomeNavigation()

        // Act
        val route = homeNavigation.route

        // Assert
        assertEquals("HomeNavigation", route)
    }

    @Test
    fun testNavigation() {
        composeTestRule.setContent {

            NavHost(
                navController = testNavController,
                startDestination = "homeScreen"
            ) {
                composable("homeScreen") {
                    Button(onClick = {
                        testNavController.navigate("detailScreen")
                    }) {
                        Text("Go to Detail")
                    }
                }

                composable("detailScreen") {

                }
            }
        }

        composeTestRule.onNodeWithText("Go to Detail").performClick()
        assertEquals("detailScreen", testNavController.currentBackStackEntry?.destination?.route)
    }
}
