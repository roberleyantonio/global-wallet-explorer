package br.com.dev360.globalwalletexplorer.coreshared.di

import br.com.dev360.globalwalletexplorer.coreshared.scope.AppCoroutineScope
import br.com.dev360.globalwalletexplorer.coreshared.scope.AppCoroutineScopeImpl
import br.com.dev360.globalwalletexplorer.coreshared.scope.AppDispatchers
import org.koin.dsl.module

val CoroutineModule = module {
    single<AppCoroutineScope> {
        AppCoroutineScopeImpl(AppDispatchers.Main)
    }
}