package br.com.dev360.globalwalletexplorer.featurehome.latestrates

import br.com.dev360.globalwalletexplorer.corenetwork.LatestRatesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.helper.ApiResult

const val BASE_CURRENCY = "BRL"
const val DIFFERENT_CURRENCY = "JPY"


val exception = Exception("No connection")

val latestRatesModel = LatestRatesQuery.Latest(
    baseCurrency = "BRL",
    quoteCurrency = "USD",
    quote = "1.0",
    date = "2026-01-21"
)
val listLatestRates = listOf(latestRatesModel)

val latestRatesQuery = LatestRatesQuery.Latest(BASE_CURRENCY, "USD", "0.20", "2026-01-21")

val latestRatesSuccessResult = ApiResult.Success(listOf(latestRatesModel))
val latestQuery: ApiResult.Success<LatestRatesQuery.Data> = ApiResult.Success(
    data = LatestRatesQuery.Data(
        latest = listOf(latestRatesQuery)
    )
)

const val EXPECTED_ERROR_CODE = 500
val serverError = ApiResult.Failure.HttpError(500, "Internal Server Error")
val networkError = ApiResult.Failure.NetworkError(exception)
val unknownError = ApiResult.Failure.UnknownError
const val NETWORK_ERROR_MESSAGE = "network error"

const val errorMessage = "Invalid currency"
const val errorItem = "The currency code BRL is not supported in free tier"

val businessError = ApiResult.Failure.ApiError(
    message = errorMessage,
    errors = listOf(errorItem)
)