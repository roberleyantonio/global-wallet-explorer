package br.com.dev360.globalwalletexplorer.featurehome.latestrates.domain

import br.com.dev360.globalwalletexplorer.corenetwork.LatestRatesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.helper.ApiResult

class FakeLatestRatesRepository(
    private val result: ApiResult<List<LatestRatesQuery.Latest>>
) : LatestRatesContracts.Repository {

    var lastBase: String? = null

    override suspend fun getLatestRates(base: String): ApiResult<List<LatestRatesQuery.Latest>> {
        lastBase = base
        return result
    }
}
