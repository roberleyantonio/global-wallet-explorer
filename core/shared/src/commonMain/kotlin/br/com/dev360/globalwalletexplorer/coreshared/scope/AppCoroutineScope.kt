package br.com.dev360.globalwalletexplorer.coreshared.scope

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

interface AppCoroutineScope : CoroutineScope

class AppCoroutineScopeImpl(
    dispatcher: CoroutineDispatcher
) : AppCoroutineScope {

    override val coroutineContext =
        dispatcher + SupervisorJob()
}