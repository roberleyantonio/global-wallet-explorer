package br.com.dev360.globalwalletexplorer.featurehome.latestrates.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.domain.model.LatestRate

@Composable
fun RateItem(rate: LatestRate) {
    ListItem(
        headlineContent = {
            Text(
                text = "${rate.baseCurrency} / ${rate.quoteCurrency}",
                style = MaterialTheme.typography.titleMedium
            )
        },
        supportingContent = {
            Text(text = "Date: ${rate.date}", style = MaterialTheme.typography.bodySmall)
        },
        trailingContent = {
            Text(
                text = rate.quote,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    )
    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
}