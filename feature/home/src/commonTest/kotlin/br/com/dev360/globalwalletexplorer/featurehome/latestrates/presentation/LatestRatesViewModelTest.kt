package br.com.dev360.globalwalletexplorer.featurehome.latestrates.presentation

import app.cash.turbine.test
import br.com.dev360.globalwalletexplorer.corenetwork.LatestRatesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.helper.ApiResult
import br.com.dev360.globalwalletexplorer.coreshared.scope.AppCoroutineScope
import br.com.dev360.globalwalletexplorer.coreshared.scope.TestAppCoroutineScope
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.BASE_CURRENCY
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.DIFFERENT_CURRENCY
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.domain.FakeLatestRatesRepository
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.latestRatesSuccessResult
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.networkError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class LatestRatesViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private lateinit var scope: AppCoroutineScope
    private lateinit var repository: FakeLatestRatesRepository
    private lateinit var viewModel: LatestRatesViewModel

    private fun createViewModel(
        result: ApiResult<List<LatestRatesQuery.Latest>>
    ) {
        repository = FakeLatestRatesRepository(result)
        viewModel = LatestRatesViewModel(
            repository = repository,
            scope = scope
        )
    }

    @BeforeTest
    fun setup() {
        scope = TestAppCoroutineScope(dispatcher)
    }

    @Test
    fun `success flow`() = runTest(dispatcher) {
        createViewModel(latestRatesSuccessResult)

        viewModel.uiState.test {
            awaitItem()

            viewModel.getLatestRates(BASE_CURRENCY)
            runCurrent()

            assertTrue(awaitItem().isLoading)

            advanceUntilIdle()

            val state = awaitItem()
            assertFalse(state.isLoading)
            assertFalse(state.isError)
            assertEquals(
                latestRatesSuccessResult.data.size,
                state.latestRateList.size
            )

            assertEquals(BASE_CURRENCY, repository.lastBase)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `error flow`() = runTest(dispatcher) {
        createViewModel(networkError)

        viewModel.uiState.test {
            awaitItem()

            viewModel.getLatestRates(BASE_CURRENCY)
            runCurrent()

            assertTrue(awaitItem().isLoading)

            advanceUntilIdle()

            val state = awaitItem()
            assertTrue(state.isError)
            assertFalse(state.isLoading)

            assertEquals(BASE_CURRENCY, repository.lastBase)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `go back event`() = runTest(dispatcher) {
        createViewModel(latestRatesSuccessResult)

        viewModel.events.test {
            viewModel.goToBack()
            runCurrent()
            assertTrue(awaitItem() is LatestRatesEvent.GoToBack)
        }
    }

    @Test
    fun `repository receives correct base`() = runTest(dispatcher) {
        createViewModel(latestRatesSuccessResult)

        viewModel.getLatestRates(DIFFERENT_CURRENCY)
        advanceUntilIdle()

        assertEquals(DIFFERENT_CURRENCY, repository.lastBase)
    }
}