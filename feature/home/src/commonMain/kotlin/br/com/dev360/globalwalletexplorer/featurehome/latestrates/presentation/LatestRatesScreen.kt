package br.com.dev360.globalwalletexplorer.featurehome.latestrates.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.components.RateItem
import globalwalletexplorer.feature.home.generated.resources.Res
import globalwalletexplorer.feature.home.generated.resources.latest_rates_screen_title
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LatestRatesScreen(
    navController: NavController,
    base: String,
    viewModel: LatestRatesViewModel = koinInject()
) {

    val state = viewModel.uiState.collectAsState()
    val latestRateList = state.value.latestRateList

    val events = { events: LatestRatesEvent ->
        if (events is LatestRatesEvent.GoToBack) {
            navController.popBackStack()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getLatestRates(base)
        viewModel.events.collectLatest { events(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.latest_rates_screen_title)) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.goToBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }

                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (state.value.isLoading) {
                CircularProgressIndicator()
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(latestRateList) { rate ->
                        RateItem(rate)
                    }
                }
            }
        }
    }
}