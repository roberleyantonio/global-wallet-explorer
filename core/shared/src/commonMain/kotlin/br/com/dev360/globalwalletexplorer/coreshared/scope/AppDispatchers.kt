@file:Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")

package br.com.dev360.globalwalletexplorer.coreshared.scope

import kotlinx.coroutines.MainCoroutineDispatcher

expect object AppDispatchers {
    val Main: MainCoroutineDispatcher
}