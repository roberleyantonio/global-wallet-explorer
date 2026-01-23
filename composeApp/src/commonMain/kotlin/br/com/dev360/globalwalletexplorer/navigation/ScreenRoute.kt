package br.com.dev360.globalwalletexplorer.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenRoute{
    @Serializable
    data object CurrenciesScreen: ScreenRoute()

    @Serializable
    data class LatestRatesScreen(val base: String): ScreenRoute()
}