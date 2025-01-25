package com.example.cat.di

import androidx.lifecycle.SavedStateHandle
import com.example.cat.core.constant.ID_CAT
import com.example.cat.data.di.dataModule
import com.example.cat.domain.di.domainModule
import com.example.cat.feature_detail.di.featureDetailModule
import com.example.cat.feature_home.di.featureHomeModule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.check.checkModules


@ExperimentalCoroutinesApi
class KoinModuleTest : KoinTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun `check koin modules`() = runTest(testDispatcher) {
        val mockContext = mockk<android.content.Context>(relaxed = true)
        val mockSavedStateHandle = mockk<SavedStateHandle>(relaxed = true)
        every { mockSavedStateHandle.get<String>(ID_CAT) } returns "123"
        checkModules {
            modules(
                module {
                    single { mockContext }
                    single { mockSavedStateHandle }
                } + dataModule + domainModule + featureHomeModule + featureDetailModule
            )
        }
    }
}