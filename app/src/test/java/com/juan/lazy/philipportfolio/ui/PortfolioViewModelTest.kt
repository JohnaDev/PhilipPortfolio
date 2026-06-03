package com.juan.lazy.philipportfolio.ui

import app.cash.turbine.test
import com.juan.lazy.philipportfolio.data.PortfolioRepository
import com.juan.lazy.philipportfolio.model.AppTheme
import com.juan.lazy.philipportfolio.model.PortfolioUiState
import com.juan.lazy.philipportfolio.model.SyncStatus
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PortfolioViewModelTest {

    private val repository: PortfolioRepository = mockk()
    private lateinit var viewModel: PortfolioViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `viewModel initializes with loading state and then emits repository data`() = runTest {
        // Given
        val expectedState = PortfolioUiState(isLoading = false, name = "John Doe")
        every { repository.getPortfolioData() } returns flowOf(expectedState)

        // When
        viewModel = PortfolioViewModel(repository)

        // Then
        viewModel.uiState.test {
            val initialState = awaitItem()
            assertEquals(true, initialState.isLoading)

            val secondState = awaitItem()
            assertEquals("John Doe", secondState.name)
            assertEquals(false, secondState.isLoading)
        }
    }

    @Test
    fun `onThemeSelected updates uiState with selected theme`() = runTest {
        // Given
        every { repository.getPortfolioData() } returns flowOf(PortfolioUiState(isLoading = false))
        viewModel = PortfolioViewModel(repository)

        // Then
        viewModel.uiState.test {
            val initialState = awaitItem()
            assertEquals(AppTheme.SYSTEM, initialState.selectedTheme)

            // When
            viewModel.onThemeSelected(AppTheme.DARK)

            val updatedState = awaitItem()
            assertEquals(AppTheme.DARK, updatedState.selectedTheme)
        }
    }

    @Test
    fun `sync status success resets to idle after 2 seconds`() = runTest {
        // Given
        val successState = PortfolioUiState(isLoading = false, syncStatus = SyncStatus.SUCCESS)
        every { repository.getPortfolioData() } returns flowOf(successState)
        viewModel = PortfolioViewModel(repository)

        // Then
        viewModel.uiState.test {
            // Initial state might be the loading state or the success state depending on how fast combine/stateIn works
            var lastState = awaitItem()
            
            if (lastState.isLoading) {
                lastState = awaitItem()
            }
            
            assertEquals(SyncStatus.SUCCESS, lastState.syncStatus)

            // Fast forward time by 2 seconds
            advanceTimeBy(2001)

            val stateAfterDelay = awaitItem()
            assertEquals(SyncStatus.IDLE, stateAfterDelay.syncStatus)
        }
    }
}
