package br.com.dev360.globalwalletexplorer.featurehome.latestrates.presentation

import app.cash.turbine.test
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.BASE_CURRENCY
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.DIFFERENT_CURRENCY
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.domain.LatestRatesContracts
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.latestRatesSuccessResult
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.networkError
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifyNoMoreCalls
import dev.mokkery.verifySuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LatestRatesViewModelTest {

    private val repository = mock<LatestRatesContracts.Repository>()
    private lateinit var viewModel: LatestRatesViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = LatestRatesViewModel(repository)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
        verifyNoMoreCalls(repository)
    }

    @Test
    fun `should emit Success state when repository returns data`() = runTest {
        everySuspend { repository.getLatestRates(BASE_CURRENCY) } returns latestRatesSuccessResult

        viewModel.uiState.test {
            assertEquals(LatestRatesUiState(), awaitItem())

            viewModel.getLatestRates(BASE_CURRENCY)

            runCurrent()
            assertTrue(awaitItem().isLoading)

            advanceUntilIdle()

            val successState = awaitItem()
            assertFalse(successState.isLoading)
            assertFalse(successState.isError)
            assertEquals(latestRatesSuccessResult.data.size, successState.latestRateList.size)

            verifySuspend { repository.getLatestRates(BASE_CURRENCY) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should emit Error state when repository returns failure`() = runTest {
        everySuspend { repository.getLatestRates(BASE_CURRENCY) } returns networkError

        viewModel.uiState.test {
            assertEquals(LatestRatesUiState(), awaitItem())

            viewModel.getLatestRates(BASE_CURRENCY)

            runCurrent()
            assertTrue(awaitItem().isLoading)

            advanceUntilIdle()

            val errorState = awaitItem()
            assertTrue(errorState.isError)
            assertFalse(errorState.isLoading)

            verifySuspend { repository.getLatestRates(BASE_CURRENCY) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should emit GoToBack event when goToBack is called`() = runTest {
        viewModel.events.test {
            viewModel.goToBack()
            runCurrent()
            assertTrue(awaitItem() is LatestRatesEvent.GoToBack)
        }
    }

    @Test
    fun `should call repository with correct parameters`() = runTest {
        everySuspend { repository.getLatestRates(DIFFERENT_CURRENCY) } returns latestRatesSuccessResult

        viewModel.getLatestRates(DIFFERENT_CURRENCY)

        advanceUntilIdle()

        verifySuspend { repository.getLatestRates(DIFFERENT_CURRENCY) }
    }
}