package br.com.dev360.globalwalletexplorer.featurehome.currencies.domain

import br.com.dev360.globalwalletexplorer.corenetwork.AvailableCurrenciesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.ConvertAmountQuery
import br.com.dev360.globalwalletexplorer.corenetwork.helper.ApiResult

class FakeCurrenciesRepository(
    private val availableCurrenciesResult: ApiResult<List<AvailableCurrenciesQuery.Currency>>? = null,
    private val convertAmountResult: ApiResult<List<ConvertAmountQuery.Convert>>? = null
) : CurrenciesContracts.Repository {

    var lastForceRefreshValue: Boolean? = null
        private set

    var getAvailableCurrenciesCalls = 0
        private set

    var convertAmountCalls = 0
        private set

    var lastConvertParams: ConvertParams? = null
        private set

    override suspend fun getAvailableCurrencies(forceRefresh: Boolean): ApiResult<List<AvailableCurrenciesQuery.Currency>> {
        getAvailableCurrenciesCalls++
        lastForceRefreshValue = forceRefresh
        return availableCurrenciesResult
            ?: error("availableCurrenciesResult not provided")
    }

    override suspend fun convertAmount(
        amount: String,
        from: String,
        to: List<String>,
        date: String?
    ): ApiResult<List<ConvertAmountQuery.Convert>> {
        convertAmountCalls++
        lastConvertParams = ConvertParams(amount, from, to, date)
        return convertAmountResult
            ?: error("convertAmountResult not provided")
    }

    data class ConvertParams(
        val amount: String,
        val from: String,
        val to: List<String>,
        val date: String?
    )
}