package br.com.dev360.globalwalletexplorer.featurehome.latestrates.presentation

import br.com.dev360.globalwalletexplorer.corenetwork.helper.onHandle
import br.com.dev360.globalwalletexplorer.coreshared.scope.AppCoroutineScope
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
    val latestRateList: List<LatestRate> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
)

sealed class LatestRatesEvent {
    data object GoToBack : LatestRatesEvent()
}

class LatestRatesViewModel(
    private val repository: LatestRatesContracts.Repository,
    private val scope: AppCoroutineScope
){
    private val _uiState = MutableStateFlow(LatestRatesUiState())
    private val _events = MutableSharedFlow<LatestRatesEvent>()

    val uiState = _uiState.asStateFlow()
    val events = _events.asSharedFlow()

    fun getLatestRates(base: String) {
        scope.launch {
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
    }

    fun goToBack() {
        scope.launch {
            _events.emit(LatestRatesEvent.GoToBack)
        }
    }

}