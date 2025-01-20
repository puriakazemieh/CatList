package com.example.cat.ui.presentation.detail

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
import com.example.cat.ui.presentation.detail.DetailNavigation.Companion.ID_CAT
import com.example.cat.ui.presentation.home.HomeNavigation
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DetailNavigationTest {

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
    fun detailNavigationRouteShouldBeCorrect() {
        // Arrange
        val detailNavigation = DetailNavigation()

        // Act
        val route = detailNavigation.route

        // Assert
        assertEquals("DetailNavigation?{ID_CAT}", route)
    }


}
