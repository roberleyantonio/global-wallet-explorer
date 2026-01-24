package br.com.dev360.globalwalletexplorer.coreshared.scope

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.SupervisorJob

class TestAppCoroutineScope(
    dispatcher: CoroutineDispatcher
) : AppCoroutineScope {

    override val coroutineContext =
        dispatcher + SupervisorJob()
}