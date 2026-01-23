package br.com.dev360.globalwalletexplorer.featurehome.latestrates.domain

import br.com.dev360.globalwalletexplorer.corenetwork.LatestRatesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.helper.ApiResult

interface LatestRatesContracts {
    interface DataSource {
        suspend fun getLatestRates(
            base: String,
        ): ApiResult<List<LatestRatesQuery.Latest>>
    }

    interface Repository {
        suspend fun getLatestRates(
            base: String,
        ): ApiResult<List<LatestRatesQuery.Latest>>
    }
}