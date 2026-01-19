package br.com.dev360.globalwalletexplorer.featurehome.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import br.com.dev360.globalwalletexplorer.featurehome.domain.model.CurrencyItem

@Composable
fun CurrencyList(currencies: List<CurrencyItem>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            count = currencies.size,
            key = { index -> currencies[index].code },
            contentType = { "currency_item" }
        ) { index ->
            val currency = currencies[index]

            Column {
                ListItem(
                    headlineContent = { Text(currency.name) },
                    supportingContent = { Text(currency.code) },
                    leadingContent = { Text(text = currency.symbol) }
                )
                HorizontalDivider()
            }
        }
    }
}