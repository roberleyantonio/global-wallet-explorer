package br.com.dev360.globalwalletexplorer.featurehome.latestrates.data

import br.com.dev360.globalwalletexplorer.corenetwork.LatestRatesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.helper.ApiResult
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.domain.LatestRatesContracts
import com.apollographql.apollo.ApolloClient
import kotlinx.coroutines.delay

class LatestRatesDataSourceImpl(
    private val apolloClient: ApolloClient
): LatestRatesContracts.DataSource {

    override suspend fun getLatestRates(
        base: String,
    ): ApiResult<List<LatestRatesQuery.Latest>> {

        /** The SWOP API recently restricted their free tier for specific base currency queries. As a Senior Developer, I prioritized app stability for this demo, so I implemented a Mock data strategy in the Data Source layer. This ensures the UI/UX remains functional while we evaluate a more robust data provider like Bloomberg or Reuters for a production environment.*/

        /*return apolloClient.safeQuery(
            LatestRatesQuery(
                base = Optional.present(base)
            )
        ).mapSuccess { it.latest }*/

        delay(1000)

        val mockData = listOf(
            LatestRatesQuery.Latest(base, "USD", "0.20", "2026-01-21"),
            LatestRatesQuery.Latest(base, "EUR", "0.18", "2026-01-21"),
            LatestRatesQuery.Latest(base, "CAD", "0.27", "2026-01-21"),
            LatestRatesQuery.Latest(base, "JPY", "29.50", "2026-01-21")
        )

        return ApiResult.Success(mockData)
    }
}