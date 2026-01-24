package br.com.dev360.globalwalletexplorer.featurehome.latestrates.di

import br.com.dev360.globalwalletexplorer.corenetwork.di.qualifiers.CurrenciesQualifier
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.data.LatestRatesDataSourceImpl
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.data.LatestRatesRepositoryImpl
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.domain.LatestRatesContracts
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.presentation.LatestRatesViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val LatestRatesModule = module {
    factoryOf(::LatestRatesViewModel)

    factory<LatestRatesContracts.DataSource> { LatestRatesDataSourceImpl(apolloClient = get(CurrenciesQualifier)) }
    factoryOf(::LatestRatesRepositoryImpl) bind LatestRatesContracts.Repository::class

}