package br.com.dev360.globalwalletexplorer.featurehome.currencies.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import globalwalletexplorer.feature.home.generated.resources.Res
import globalwalletexplorer.feature.home.generated.resources.retry_button
import org.jetbrains.compose.resources.stringResource

@Composable
fun ErrorView(message: String, onRetry: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = onRetry) {
            Text(stringResource(Res.string.retry_button))
        }
    }
}