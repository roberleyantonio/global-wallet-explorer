package br.com.dev360.globalwalletexplorer.featurehome.currencies.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.dev360.globalwalletexplorer.coresharedui.helpers.asString
import br.com.dev360.globalwalletexplorer.featurehome.currencies.components.CurrencyList
import br.com.dev360.globalwalletexplorer.featurehome.currencies.components.ErrorView
import globalwalletexplorer.feature.home.generated.resources.Res
import globalwalletexplorer.feature.home.generated.resources.home_title
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrenciesScreen(
    onBackPressed: () -> Unit,
    navToLatestRates: (String) -> Unit,
    viewModel: CurrenciesViewModel = koinInject(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val error = uiState.error

    val handleEvents = { event: CurrenciesEvents ->
        when(event) {
            is CurrenciesEvents.GoToLatestRates -> navToLatestRates(event.base)
            CurrenciesEvents.GoToBack -> onBackPressed()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getAvailableCurrencies()

        viewModel.events.collectLatest { handleEvents(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.home_title)) },
                actions = {
                    IconButton(
                        onClick = { viewModel.getAvailableCurrencies(forceRefresh = true) },
                        enabled = !uiState.isLoading
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh Currencies"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }

                error != null -> {
                    ErrorView(
                        message = error.asString(),
                        onRetry = { viewModel.getAvailableCurrencies() }
                    )
                }

                else -> {
                    CurrencyList(
                        currencies = uiState.currencies,
                        onCurrencyClick = { viewModel.goToLatestRates(it.code) }
                    )
                }
            }
        }
    }
}