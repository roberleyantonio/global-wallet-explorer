package br.com.dev360.globalwalletexplorer.featurehome.latestrates.data

import br.com.dev360.globalwalletexplorer.corenetwork.LatestRatesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.helper.ApiResult
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.domain.LatestRatesContracts

class LatestRatesRepositoryImpl(
    private val dataSource: LatestRatesContracts.DataSource
) : LatestRatesContracts.Repository {
    override suspend fun getLatestRates(
        base: String,
    ): ApiResult<List<LatestRatesQuery.Latest>> = dataSource.getLatestRates(base)
}