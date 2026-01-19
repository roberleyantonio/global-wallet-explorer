package br.com.dev360.globalwalletexplorer.featurehome.di


import br.com.dev360.globalwalletexplorer.corenetwork.di.qualifiers.CountriesQualifier
import br.com.dev360.globalwalletexplorer.featurehome.data.HomeDataSourceImpl
import br.com.dev360.globalwalletexplorer.featurehome.data.HomeRepositoryImpl
import br.com.dev360.globalwalletexplorer.featurehome.domain.HomeContracts
import br.com.dev360.globalwalletexplorer.featurehome.presentation.HomeUiModelImpl
import br.com.dev360.globalwalletexplorer.featurehome.presentation.HomeViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val HomeModule = module {
    viewModelOf(::HomeViewModel)

    factory<HomeContracts.DataSource> { HomeDataSourceImpl(get(CountriesQualifier)) }
    factoryOf(::HomeRepositoryImpl) bind HomeContracts.Repository::class
    factoryOf(::HomeUiModelImpl) bind HomeContracts.UiModel::class
}