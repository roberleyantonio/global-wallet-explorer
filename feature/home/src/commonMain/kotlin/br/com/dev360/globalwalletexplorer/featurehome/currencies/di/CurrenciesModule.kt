package br.com.dev360.globalwalletexplorer.featurehome.currencies.di


import br.com.dev360.globalwalletexplorer.corenetwork.di.qualifiers.CurrenciesQualifier
import br.com.dev360.globalwalletexplorer.featurehome.currencies.data.CurrenciesDataSourceImpl
import br.com.dev360.globalwalletexplorer.featurehome.currencies.data.CurrenciesRepositoryImpl
import br.com.dev360.globalwalletexplorer.featurehome.currencies.domain.CurrenciesContracts
import br.com.dev360.globalwalletexplorer.featurehome.currencies.presentation.CurrenciesUiModelImpl
import br.com.dev360.globalwalletexplorer.featurehome.currencies.presentation.CurrenciesViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val CurrenciesModule = module {
    viewModelOf(::CurrenciesViewModel)

    factory<CurrenciesContracts.DataSource> { CurrenciesDataSourceImpl(get(CurrenciesQualifier)) }
    factoryOf(::CurrenciesRepositoryImpl) bind CurrenciesContracts.Repository::class
    factoryOf(::CurrenciesUiModelImpl) bind CurrenciesContracts.UiModel::class
}