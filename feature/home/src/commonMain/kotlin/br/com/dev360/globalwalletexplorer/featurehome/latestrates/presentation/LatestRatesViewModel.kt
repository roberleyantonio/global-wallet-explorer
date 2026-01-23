package br.com.dev360.globalwalletexplorer.featurehome.latestrates.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dev360.globalwalletexplorer.corenetwork.helper.onHandle
import br.com.dev360.globalwalletexplorer.featurehome.currencies.domain.CurrenciesContracts
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.domain.LatestRatesContracts
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.domain.model.LatestRate
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.domain.model.toLatestRateList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LatestRatesUiState(
    val latestRateList: List<LatestRate> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)

sealed class LatestRatesEvent {
    data object GoToBack : LatestRatesEvent()
}

class LatestRatesViewModel(
    private val repository: LatestRatesContracts.Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LatestRatesUiState())
    private val _events = MutableSharedFlow<LatestRatesEvent>()

    val uiState = _uiState.asStateFlow()
    val events = _events.asSharedFlow()

    fun getLatestRates(base: String) = viewModelScope.launch {
        flow {
            emit(repository.getLatestRates(base = base))
        }.onStart {
            _uiState.update { it.copy(isLoading = true, isError = false) }
        }.collectLatest { result ->
            result.onHandle(
                success = { data ->
                    _uiState.update { it.copy(
                        latestRateList = data.toLatestRateList(),
                        isLoading = false,
                        isError = false,
                    ) }
                },
                failure = {
                    _uiState.update { it.copy(isLoading = false, isError = true) }
                }
            )
        }
    }

    fun goToBack() = viewModelScope.launch {
        _events.emit(LatestRatesEvent.GoToBack)
    }

}