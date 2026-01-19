package br.com.dev360.globalwalletexplorer.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenRoute{
    @Serializable
    data object HomeScreen: ScreenRoute()
}