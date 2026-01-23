package br.com.dev360.globalwalletexplorer.featurehome.currencies.data

import br.com.dev360.globalwalletexplorer.corenetwork.AvailableCurrenciesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.ConvertAmountQuery
import br.com.dev360.globalwalletexplorer.corenetwork.LatestRatesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.helper.ApiResult
import br.com.dev360.globalwalletexplorer.featurehome.currencies.domain.CurrenciesContracts
import org.koin.core.annotation.Factory

@Factory
class CurrenciesRepositoryImpl(
    private val dataSource: CurrenciesContracts.DataSource
): CurrenciesContracts.Repository {
    override suspend fun getAvailableCurrencies(): ApiResult<List<AvailableCurrenciesQuery.Currency>> = dataSource.getAvailableCurrencies()

    override suspend fun convertAmount(
        amount: String,
        from: String,
        to: List<String>,
        date: String?
    ): ApiResult<List<ConvertAmountQuery.Convert>> = dataSource.convertAmount(amount, from, to, date)
}