package br.com.dev360.globalwalletexplorer.featurehome.latestrates.data

import br.com.dev360.globalwalletexplorer.corenetwork.helper.ApiResult
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.BASE_CURRENCY
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.DIFFERENT_CURRENCY
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.EXPECTED_ERROR_CODE
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.businessError
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.domain.LatestRatesContracts
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.errorItem
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.errorMessage
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.exception
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.latestRatesSuccessResult
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.listLatestRates
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.networkError
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.serverError
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.unknownError
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifyNoMoreCalls
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.AfterTest
import kotlin.test.assertTrue

class LatestRatesRepositoryImplTest {
    private val dataSource = mock<LatestRatesContracts.DataSource>()
    private val repository = LatestRatesRepositoryImpl(dataSource)

    @AfterTest
    fun teardown() {
        verifyNoMoreCalls(dataSource)
    }

    @Test
    fun `verify if repository returns data from data source`() = runTest {
        everySuspend { dataSource.getLatestRates(BASE_CURRENCY) } returns latestRatesSuccessResult

        val result = repository.getLatestRates(BASE_CURRENCY)

        verifySuspend { dataSource.getLatestRates(BASE_CURRENCY) }

        assertTrue(result is ApiResult.Success)
        assertEquals(BASE_CURRENCY, result.data.first().baseCurrency)
    }

    @Test
    fun `should pass the exact currency code to data source`() = runTest {
        everySuspend { dataSource.getLatestRates(DIFFERENT_CURRENCY) } returns latestRatesSuccessResult

        repository.getLatestRates(DIFFERENT_CURRENCY)

        verifySuspend { dataSource.getLatestRates(DIFFERENT_CURRENCY) }
    }

    @Test
    fun `should return success when data source returns success`() = runTest {
        everySuspend { dataSource.getLatestRates(BASE_CURRENCY) } returns latestRatesSuccessResult

        val result = repository.getLatestRates(BASE_CURRENCY)

        verifySuspend { dataSource.getLatestRates(BASE_CURRENCY) }

        assertTrue(result is ApiResult.Success)
        assertEquals(listLatestRates, result.data)
    }

    @Test
    fun `should return HttpError when data source returns server error`() = runTest {
        everySuspend { dataSource.getLatestRates(BASE_CURRENCY) } returns serverError

        val result = repository.getLatestRates(BASE_CURRENCY)

        verifySuspend { dataSource.getLatestRates(BASE_CURRENCY) }

        assertTrue(result is ApiResult.Failure.HttpError)
        assertEquals(EXPECTED_ERROR_CODE, result.code)
    }

    @Test
    fun `should return NetworkError when there is no internet`() = runTest {
        everySuspend { dataSource.getLatestRates(BASE_CURRENCY) } returns networkError

        val result = repository.getLatestRates(BASE_CURRENCY)

        verifySuspend { dataSource.getLatestRates(BASE_CURRENCY) }

        assertTrue(result is ApiResult.Failure.NetworkError)
        assertEquals(exception, result.throwable)
    }

    @Test
    fun `should return ApiError when API returns business validation error`() = runTest {
        everySuspend { dataSource.getLatestRates(BASE_CURRENCY) } returns businessError

        val result = repository.getLatestRates(BASE_CURRENCY)

        verifySuspend { dataSource.getLatestRates(BASE_CURRENCY) }

        assertTrue(result is ApiResult.Failure.ApiError)
        assertEquals(errorMessage, result.message)
        assertTrue(result.errors.contains(errorItem))
    }

    @Test
    fun `should return UnknownError for generic failures`() = runTest {
        everySuspend { dataSource.getLatestRates(BASE_CURRENCY) } returns unknownError

        val result = repository.getLatestRates(BASE_CURRENCY)

        verifySuspend { dataSource.getLatestRates(BASE_CURRENCY) }

        assertTrue(result is ApiResult.Failure.UnknownError)
    }
}
