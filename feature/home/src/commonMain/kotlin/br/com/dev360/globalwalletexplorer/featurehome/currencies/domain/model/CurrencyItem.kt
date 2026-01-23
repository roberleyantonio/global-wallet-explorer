package br.com.dev360.globalwalletexplorer.featurehome.currencies.domain.model

import br.com.dev360.globalwalletexplorer.corenetwork.AvailableCurrenciesQuery

data class CurrencyItem(
    val id: String,
    val name: String,
    val symbol: String,
    val code: String
)

private fun AvailableCurrenciesQuery.Currency.toCurrencyItem() = CurrencyItem(
    id = this.code,
    name = this.name,
    symbol = "",
    code = this.code
)

fun List<AvailableCurrenciesQuery.Currency>.toCurrencyList() = this.map { it.toCurrencyItem() }