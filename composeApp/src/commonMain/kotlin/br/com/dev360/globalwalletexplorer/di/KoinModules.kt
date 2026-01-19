package br.com.dev360.globalwalletexplorer.di

import br.com.dev360.globalwalletexplorer.corenetwork.di.networkModule
import br.com.dev360.globalwalletexplorer.featurehome.di.HomeModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null
) {
    startKoin {
        config?.invoke(this)
        modules(
            networkModule,
            HomeModule,
        )
    }
}