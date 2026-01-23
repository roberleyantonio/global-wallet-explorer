package br.com.dev360.globalwalletexplorer.featurehome.currencies.presentation

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import br.com.dev360.globalwalletexplorer.coresharedui.helpers.UiText
import br.com.dev360.globalwalletexplorer.featurehome.CONNECTION_ERROR_MESSAGE
import br.com.dev360.globalwalletexplorer.featurehome.USD_CURRENCY
import br.com.dev360.globalwalletexplorer.featurehome.currenciesList
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class CurrenciesScreenTest {

    private val viewModelMock = mock<CurrenciesViewModelInterface>()

    private val uiState = MutableStateFlow(CurrenciesUiState())
    private val events = MutableSharedFlow<CurrenciesEvents>()

    private fun setupMocks() {
        every { viewModelMock.uiState } returns uiState
        every { viewModelMock.events } returns events

        every { viewModelMock.getAvailableCurrencies() } returns Unit
        every { viewModelMock.goToLatestRates(any()) } returns Unit
    }

    @Test
    fun shouldShowProgressIndicatorWhenLoading() = runComposeUiTest {
        setupMocks()
        uiState.value = CurrenciesUiState(isLoading = true)

        setContent {
            CurrenciesScreen(
                onBackPressed = {},
                viewModel = viewModelMock,
                navToLatestRates = {}
            )
        }

        onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate)).assertIsDisplayed()
    }

    @Test
    fun shouldShowErrorViewWhenStateHasError() = runComposeUiTest {
        setupMocks()

        uiState.value = CurrenciesUiState(
            isLoading = false,
            error = UiText.DynamicString(CONNECTION_ERROR_MESSAGE)
        )

        setContent {
            CurrenciesScreen(
                onBackPressed = {},
                viewModel = viewModelMock,
                navToLatestRates = {}
            )
        }

        onNodeWithText(CONNECTION_ERROR_MESSAGE).assertIsDisplayed()
    }

    @Test
    fun shouldCallViewModelWhenCurrencyIsClicked() = runComposeUiTest {
        setupMocks()

        uiState.value = CurrenciesUiState(isLoading = false, currencies = currenciesList)

        setContent {
            CurrenciesScreen(
                onBackPressed = {},
                viewModel = viewModelMock,
                navToLatestRates = {}
            )
        }

        onNodeWithText(USD_CURRENCY).performClick()

        verify { viewModelMock.goToLatestRates(USD_CURRENCY) }
    }
}