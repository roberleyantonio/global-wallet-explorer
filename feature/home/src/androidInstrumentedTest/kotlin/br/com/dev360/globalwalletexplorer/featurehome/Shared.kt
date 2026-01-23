package br.com.dev360.globalwalletexplorer.featurehome

import br.com.dev360.globalwalletexplorer.featurehome.currencies.domain.model.CurrencyItem

const val CONNECTION_ERROR_MESSAGE = "Erro de conexão"
const val USD_CURRENCY = "USD"

val currenciesList = listOf(
    CurrencyItem(
        id = "1",
        name = "Dollar",
        symbol = "$",
        code = "USD"
    )
)