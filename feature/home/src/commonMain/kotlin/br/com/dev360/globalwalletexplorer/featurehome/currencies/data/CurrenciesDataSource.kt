package br.com.dev360.globalwalletexplorer.featurehome.currencies.data

import br.com.dev360.globalwalletexplorer.corenetwork.AvailableCurrenciesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.ConvertAmountQuery
import br.com.dev360.globalwalletexplorer.corenetwork.extensions.safeQuery
import br.com.dev360.globalwalletexplorer.corenetwork.helper.ApiResult
import br.com.dev360.globalwalletexplorer.corenetwork.helper.mapSuccess
import br.com.dev360.globalwalletexplorer.featurehome.currencies.domain.CurrenciesContracts
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.apollographql.apollo.cache.normalized.FetchPolicy
import org.koin.core.annotation.Factory

@Factory
class CurrenciesDataSourceImpl(
    private val apolloClient: ApolloClient
) : CurrenciesContracts.DataSource {
    override suspend fun getAvailableCurrencies(forceRefresh: Boolean): ApiResult<List<AvailableCurrenciesQuery.Currency>> {
        val policy = if (forceRefresh) FetchPolicy.NetworkFirst else null
        return apolloClient
            .safeQuery(query = AvailableCurrenciesQuery(), policy = policy)
            .mapSuccess { it.currencies }
    }


    override suspend fun convertAmount(
        amount: String,
        from: String,
        to: List<String>,
        date: String?
    ): ApiResult<List<ConvertAmountQuery.Convert>> {
        return apolloClient.safeQuery(
            ConvertAmountQuery(
                amount = amount,
                base = from,
                quotes = Optional.present(to),
                date = Optional.presentIfNotNull(date)
            )
        ).mapSuccess { it.convert }
    }
}
