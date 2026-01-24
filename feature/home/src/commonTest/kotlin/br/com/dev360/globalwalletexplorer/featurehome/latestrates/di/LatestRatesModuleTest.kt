package br.com.dev360.globalwalletexplorer.featurehome.latestrates.di

import androidx.lifecycle.SavedStateHandle
import br.com.dev360.globalwalletexplorer.corenetwork.di.qualifiers.CurrenciesQualifier
import br.com.dev360.globalwalletexplorer.coreshared.scope.AppCoroutineScope
import br.com.dev360.globalwalletexplorer.coreshared.scope.TestAppCoroutineScope
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.data.LatestRatesDataSourceImpl
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.data.LatestRatesRepositoryImpl
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.domain.LatestRatesContracts
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.presentation.LatestRatesViewModel
import com.apollographql.apollo.ApolloClient
import kotlinx.coroutines.test.StandardTestDispatcher
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertNotNull


class LatestRatesModuleTest : KoinTest {
    private val testDispatcher = StandardTestDispatcher()

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `should resolve latest rates dependencies`() {
        startKoin {
            modules(
                module {
                    single(CurrenciesQualifier) {
                        ApolloClient.Builder()
                            .serverUrl("https://localhost")
                            .build()
                    }
                    factory { SavedStateHandle() }

                    single<AppCoroutineScope> {
                        TestAppCoroutineScope(testDispatcher)
                    }
                },
                LatestRatesModule
            )
        }

        val viewModel = get<LatestRatesViewModel>()
        assertNotNull(viewModel)

        val repo = get<LatestRatesContracts.Repository>()
        assertIs<LatestRatesRepositoryImpl>(repo)

        val dataSource = get<LatestRatesContracts.DataSource>()
        assertIs<LatestRatesDataSourceImpl>(dataSource)
    }
}