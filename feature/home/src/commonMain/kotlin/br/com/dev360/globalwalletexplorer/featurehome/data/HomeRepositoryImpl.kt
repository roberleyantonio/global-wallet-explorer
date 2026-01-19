package br.com.dev360.globalwalletexplorer.featurehome.data

import br.com.dev360.globalwalletexplorer.corenetwork.AvailableCurrenciesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.ConvertAmountQuery
import br.com.dev360.globalwalletexplorer.corenetwork.LatestRatesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.helper.ApiResult
import br.com.dev360.globalwalletexplorer.featurehome.domain.HomeContracts
import org.koin.core.annotation.Factory

@Factory
class HomeRepositoryImpl(
    private val dataSource: HomeContracts.DataSource
): HomeContracts.Repository {
    override suspend fun getAvailableCurrencies(): ApiResult<List<AvailableCurrenciesQuery.Currency>> = dataSource.getAvailableCurrencies()

    override suspend fun getLatestRates(
        base: String,
        quotes: List<String>
    ): ApiResult<List<LatestRatesQuery.Latest>> = dataSource.getLatestRates(base, quotes)

    override suspend fun convertAmount(
        amount: String,
        from: String,
        to: List<String>,
        date: String?
    ): ApiResult<List<ConvertAmountQuery.Convert>> = dataSource.convertAmount(amount, from, to, date)
}