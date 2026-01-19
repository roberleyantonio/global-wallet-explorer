package br.com.dev360.globalwalletexplorer.featurehome.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import br.com.dev360.globalwalletexplorer.coresharedui.helpers.asString
import br.com.dev360.globalwalletexplorer.featurehome.components.CurrencyList
import br.com.dev360.globalwalletexplorer.featurehome.components.ErrorView
import globalwalletexplorer.feature.home.generated.resources.Res
import globalwalletexplorer.feature.home.generated.resources.home_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val error = uiState.error

    LaunchedEffect(Unit) {
        viewModel.getAvailableCurrencies()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(stringResource(Res.string.home_title)) })
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
                    CurrencyList(uiState.currencies)
                }
            }
        }
    }
}