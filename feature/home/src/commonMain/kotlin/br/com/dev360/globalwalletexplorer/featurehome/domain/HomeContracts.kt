package br.com.dev360.globalwalletexplorer.featurehome.domain

import br.com.dev360.globalwalletexplorer.corenetwork.AvailableCurrenciesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.ConvertAmountQuery
import br.com.dev360.globalwalletexplorer.corenetwork.LatestRatesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.helper.ApiResult
import br.com.dev360.globalwalletexplorer.coresharedui.helpers.UiText
import br.com.dev360.globalwalletexplorer.featurehome.domain.model.CurrencyItem

interface HomeContracts {
    interface DataSource {
        suspend fun getAvailableCurrencies(): ApiResult<List<AvailableCurrenciesQuery.Currency>>
        suspend fun getLatestRates(
            base: String,
            quotes: List<String>
        ): ApiResult<List<LatestRatesQuery.Latest>>
        suspend fun convertAmount(
            amount: String,
            from: String,
            to: List<String>,
            date: String? = null
        ): ApiResult<List<ConvertAmountQuery.Convert>>
    }

    interface Repository {
        suspend fun getAvailableCurrencies(): ApiResult<List<AvailableCurrenciesQuery.Currency>>
        suspend fun getLatestRates(
            base: String,
            quotes: List<String>
        ): ApiResult<List<LatestRatesQuery.Latest>>
        suspend fun convertAmount(
            amount: String,
            from: String,
            to: List<String>,
            date: String? = null
        ): ApiResult<List<ConvertAmountQuery.Convert>>
    }

    interface UiModel {
        fun getFailureText(failure: ApiResult.Failure): UiText
        fun mapToCurrencyList(apiData: List<AvailableCurrenciesQuery.Currency>): List<CurrencyItem>
    }
}