package com.example.cat.core

import com.example.cat.core.ui.presentration.base.BaseViewModel
import com.example.cat.core.ui.presentration.base.UiEffect
import com.example.cat.core.ui.presentration.base.UiEvent
import com.example.cat.core.ui.presentration.base.UiState
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


@ExperimentalCoroutinesApi
class BaseViewModelTest {

    private lateinit var baseViewModel: BaseViewModel<TestEvent, TestState, TestEffect>

    private val mockEventHandler: (TestEvent) -> Unit =
        mockk(relaxed = true)

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var testViewModel: TestViewModel

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        baseViewModel = object : BaseViewModel<TestEvent, TestState, TestEffect>() {
            override fun createInitialState(): TestState = TestState.Initial

            override fun handleEvent(event: TestEvent) {
                mockEventHandler(event)
            }
        }
        testViewModel = TestViewModel()
    }

    @AfterEach
    fun tearDown() {
        // Reset the Main dispatcher
        Dispatchers.resetMain()
    }

    @Test
    fun `test initial state`() = runTest(testDispatcher) {
        val initialState = baseViewModel.uiState.first()
        assertEquals(TestState.Initial, initialState)
    }

    @Test
    fun `test setEvent`() = runTest(testDispatcher) {
        val event = TestEvent.SomeEvent
        baseViewModel.setEvent(event)
        coVerify { mockEventHandler(event) }
    }


    @Test
    fun `test setState updates the state`() = runTest(testDispatcher) {
        // Define the new state
        val newState = TestState.Updated

        // Call the public method to update the state
        testViewModel.publicSetState { newState }

        // Verify that the state has been updated
        val updatedState = testViewModel.uiState.first()
        assertEquals(newState, updatedState)
    }


    @Test
    fun `test setEffect`() = runTest(testDispatcher) {
        val effect = TestEffect.SomeEffect
        testViewModel.publicSetEffect { effect }
        val emittedEffect = testViewModel.effect.first()
        assertEquals(effect, emittedEffect)
    }

    sealed class TestEvent : UiEvent {
        object SomeEvent : TestEvent()
    }

    sealed class TestState : UiState {
        object Initial : TestState()
        object Updated : TestState()
    }

    sealed class TestEffect : UiEffect {
        object SomeEffect : TestEffect()
    }

    class TestViewModel : BaseViewModel<TestEvent, TestState, TestEffect>() {
        override fun createInitialState(): TestState = TestState.Initial

        override fun handleEvent(event: TestEvent) {
            // Handle events here
        }

        // Expose protected methods for testing
        fun publicSetEffect(builder: () -> TestEffect) {
            setEffect(builder)
        }

        fun publicSetState(reduce: TestState.() -> TestState) {
            setState(reduce)
        }
    }
}