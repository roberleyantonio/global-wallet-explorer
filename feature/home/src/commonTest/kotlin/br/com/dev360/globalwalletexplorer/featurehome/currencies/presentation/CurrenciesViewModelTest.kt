package br.com.dev360.globalwalletexplorer.featurehome.currencies.presentation

import br.com.dev360.globalwalletexplorer.corenetwork.AvailableCurrenciesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.helper.ApiResult
import br.com.dev360.globalwalletexplorer.coreshared.scope.TestAppCoroutineScope
import br.com.dev360.globalwalletexplorer.coresharedui.helpers.UiText
import br.com.dev360.globalwalletexplorer.featurehome.currencies.domain.FakeCurrenciesRepository
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.BASE_CURRENCY
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.NETWORK_ERROR_MESSAGE
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class CurrenciesViewModelTest {

    @Test
    fun `success updates currencies and stops loading`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)

        val repository = FakeCurrenciesRepository(
            availableCurrenciesResult = ApiResult.Success(emptyList())
        )

        val scope = TestAppCoroutineScope(dispatcher)

        val viewModel = CurrenciesViewModel(
            uiModel = FakeCurrenciesUiModel(UiText.DynamicString(NETWORK_ERROR_MESSAGE)),
            repository = repository,
            scope = scope
        )

        viewModel.getAvailableCurrencies()
        advanceUntilIdle()

        val state = viewModel.uiState.first()

        assertEquals(0, state.currencies.size)
        assertFalse(state.isLoading)
        assertEquals(UiText.DynamicString(NETWORK_ERROR_MESSAGE),state.error)
        assertEquals(1, repository.getAvailableCurrenciesCalls)
    }

    @Test
    fun `success updates currencies  with forceRefresh true and stops loading`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)

        val repository = FakeCurrenciesRepository(
            availableCurrenciesResult = ApiResult.Success(emptyList())
        )
        val scope = TestAppCoroutineScope(dispatcher)

        val viewModel = CurrenciesViewModel(
            uiModel = FakeCurrenciesUiModel(UiText.DynamicString("")),
            repository = repository,
            scope = scope
        )

        viewModel.getAvailableCurrencies(forceRefresh = true)
        advanceUntilIdle()

        val state = viewModel.uiState.first()

        assertEquals(1, repository.getAvailableCurrenciesCalls)
        assertEquals(true, repository.lastForceRefreshValue)
        assertFalse(state.isLoading)
    }

    @Test
    fun `failure exposes error and stops loading`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)
        val errorText = UiText.DynamicString(NETWORK_ERROR_MESSAGE)

        val repository = FakeCurrenciesRepository(
            availableCurrenciesResult = ApiResult.Failure.NetworkError(Throwable())
        )

        val scope = TestAppCoroutineScope(dispatcher)

        val viewModel = CurrenciesViewModel(
            uiModel = FakeCurrenciesUiModel(errorText),
            repository = repository,
            scope = scope
        )

        viewModel.getAvailableCurrencies()
        advanceUntilIdle()

        val state = viewModel.uiState.first()

        assertEquals(errorText, state.error)
        assertFalse(state.isLoading)
        assertEquals(1, repository.getAvailableCurrenciesCalls)
    }

    @Test
    fun `goToLatestRates emits navigation event only`() = runTest {
        val dispatcher = StandardTestDispatcher(testScheduler)

        val repository = FakeCurrenciesRepository(
            availableCurrenciesResult = ApiResult.Success(emptyList())
        )

        val scope = TestAppCoroutineScope(dispatcher)

        val viewModel = CurrenciesViewModel(
            uiModel = FakeCurrenciesUiModel(UiText.DynamicString("")),
            repository = repository,
            scope = scope
        )

        viewModel.goToLatestRates(BASE_CURRENCY)
        advanceUntilIdle()

        val event = viewModel.events.first()

        assertEquals(CurrenciesEvents.GoToLatestRates(BASE_CURRENCY), event)
        assertEquals(0, repository.getAvailableCurrenciesCalls)
    }
}