package br.com.dev360.globalwalletexplorer.featurehome.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.dev360.globalwalletexplorer.corenetwork.LatestRatesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.helper.ApiResult
import br.com.dev360.globalwalletexplorer.corenetwork.helper.onHandle
import br.com.dev360.globalwalletexplorer.coresharedui.helpers.UiText
import br.com.dev360.globalwalletexplorer.featurehome.domain.HomeContracts
import br.com.dev360.globalwalletexplorer.featurehome.domain.model.CurrencyItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

data class HomeUiState(
    val currencies: List<CurrencyItem> = emptyList(),
    val rates: List<LatestRatesQuery.Latest> = emptyList(),
    val error: UiText? = null,
    val isLoading: Boolean = false
)

@KoinViewModel
class HomeViewModel(
    private val uiModel: HomeContracts.UiModel,
    private val repository: HomeContracts.Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    fun getAvailableCurrencies() = viewModelScope.launch {
        flow {
            emit(repository.getAvailableCurrencies())
        }.onStart {
            _uiState.update { it.copy(isLoading = true) }
        }.collectLatest { result ->

            result.onHandle(
                success = { data ->
                    val mappedCurrencies = uiModel.mapToCurrencyList(data)

                    _uiState.update {
                        it.copy(
                            currencies = mappedCurrencies,
                            isLoading = false
                        )
                    }
                },
                failure = { error -> handleFailure(error) }
            )
        }
    }

    private fun handleFailure(failure: ApiResult.Failure) {
        val errorText = uiModel.getFailureText(failure)
        _uiState.update { it.copy(error = errorText, isLoading = false) }
    }
}