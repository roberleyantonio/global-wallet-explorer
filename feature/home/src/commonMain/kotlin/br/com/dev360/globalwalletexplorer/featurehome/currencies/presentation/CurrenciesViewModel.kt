package br.com.dev360.globalwalletexplorer.featurehome.currencies.presentation

import br.com.dev360.globalwalletexplorer.corenetwork.LatestRatesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.helper.ApiResult
import br.com.dev360.globalwalletexplorer.corenetwork.helper.onHandle
import br.com.dev360.globalwalletexplorer.coreshared.scope.AppCoroutineScope
import br.com.dev360.globalwalletexplorer.coresharedui.helpers.UiText
import br.com.dev360.globalwalletexplorer.featurehome.currencies.domain.CurrenciesContracts
import br.com.dev360.globalwalletexplorer.featurehome.currencies.domain.model.CurrencyItem
import br.com.dev360.globalwalletexplorer.featurehome.currencies.domain.model.toCurrencyList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CurrenciesUiState(
    val currencies: List<CurrencyItem> = emptyList(),
    val rates: List<LatestRatesQuery.Latest> = emptyList(),
    val error: UiText? = null,
    val isLoading: Boolean = false
)

sealed class CurrenciesEvents {
    data class GoToLatestRates(val base: String) : CurrenciesEvents()
    data object GoToBack: CurrenciesEvents()
}

interface CurrenciesViewModelInterface {
    val uiState: StateFlow<CurrenciesUiState>
    val events: SharedFlow<CurrenciesEvents>
    fun getAvailableCurrencies()
    fun goToLatestRates(base: String)
}

class CurrenciesViewModel(
    private val uiModel: CurrenciesContracts.UiModel,
    private val repository: CurrenciesContracts.Repository,
    private val scope: AppCoroutineScope
) {

    private val _uiState = MutableStateFlow(CurrenciesUiState())
    private val _events = MutableSharedFlow<CurrenciesEvents>()

    val uiState = _uiState.asStateFlow()
    val events = _events.asSharedFlow()

    fun getAvailableCurrencies() {
        scope.launch {
            flow {
                emit(repository.getAvailableCurrencies())
            }.onStart {
                _uiState.update { it.copy(isLoading = true) }
            }.collectLatest { result ->

                result.onHandle(
                    success = { data ->
                        _uiState.update {
                            it.copy(
                                currencies = data.toCurrencyList(),
                                isLoading = false
                            )
                        }
                    },
                    failure = { error -> handleFailure(error) }
                )
            }
        }
    }

    fun goToLatestRates(base: String) {
        scope.launch {
            _events.emit(CurrenciesEvents.GoToLatestRates(base))
        }
    }

    private fun handleFailure(failure: ApiResult.Failure) {
        val errorText = uiModel.getFailureText(failure)
        _uiState.update { it.copy(error = errorText, isLoading = false) }
    }
}