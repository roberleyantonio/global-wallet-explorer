package br.com.dev360.globalwalletexplorer.di

import br.com.dev360.globalwalletexplorer.corenetwork.di.networkModule
import br.com.dev360.globalwalletexplorer.coreshared.di.CoroutineModule
import br.com.dev360.globalwalletexplorer.featurehome.currencies.di.CurrenciesModule
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.di.LatestRatesModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(
            networkModule,
            CurrenciesModule,
            LatestRatesModule,
            CoroutineModule
        )
    }
}