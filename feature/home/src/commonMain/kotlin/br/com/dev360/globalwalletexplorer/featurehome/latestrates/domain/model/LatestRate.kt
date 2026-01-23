package br.com.dev360.globalwalletexplorer.featurehome.latestrates.domain.model

import br.com.dev360.globalwalletexplorer.corenetwork.LatestRatesQuery

data class LatestRate(
    val baseCurrency: String = "",
    val quoteCurrency: String = "",
    val quote: String = "",
    val date: String = ""
)

fun LatestRatesQuery.Latest.toLatestRate() = LatestRate(
    baseCurrency = this.baseCurrency,
    quoteCurrency = this.quoteCurrency,
    quote = this.quote,
    date = this.date
)

fun List<LatestRatesQuery.Latest>.toLatestRateList(): List<LatestRate> = this.map { it.toLatestRate() }