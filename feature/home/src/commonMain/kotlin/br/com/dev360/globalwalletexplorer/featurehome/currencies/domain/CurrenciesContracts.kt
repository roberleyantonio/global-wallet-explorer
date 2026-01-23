package br.com.dev360.globalwalletexplorer.featurehome.currencies.domain

import br.com.dev360.globalwalletexplorer.corenetwork.AvailableCurrenciesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.ConvertAmountQuery
import br.com.dev360.globalwalletexplorer.corenetwork.LatestRatesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.helper.ApiResult
import br.com.dev360.globalwalletexplorer.coresharedui.helpers.UiText
import br.com.dev360.globalwalletexplorer.featurehome.currencies.domain.model.CurrencyItem

interface CurrenciesContracts {
    interface DataSource {
        suspend fun getAvailableCurrencies(): ApiResult<List<AvailableCurrenciesQuery.Currency>>

        suspend fun convertAmount(
            amount: String,
            from: String,
            to: List<String>,
            date: String? = null
        ): ApiResult<List<ConvertAmountQuery.Convert>>
    }

    interface Repository {
        suspend fun getAvailableCurrencies(): ApiResult<List<AvailableCurrenciesQuery.Currency>>

        suspend fun convertAmount(
            amount: String,
            from: String,
            to: List<String>,
            date: String? = null
        ): ApiResult<List<ConvertAmountQuery.Convert>>
    }

    interface UiModel {
        fun getFailureText(failure: ApiResult.Failure): UiText
    }
}