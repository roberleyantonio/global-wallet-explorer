package br.com.dev360.globalwalletexplorer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import br.com.dev360.globalwalletexplorer.featurehome.currencies.presentation.CurrenciesScreen
import br.com.dev360.globalwalletexplorer.featurehome.latestrates.presentation.LatestRatesScreen

@Composable
fun SetupNavGraph(
    startDestination: ScreenRoute = ScreenRoute.CurrenciesScreen
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<ScreenRoute.CurrenciesScreen> {
            CurrenciesScreen(
                navController = navController,
                navToLatestRates = { navController.navigate(ScreenRoute.LatestRatesScreen(it)) }
            )
        }

        composable<ScreenRoute.LatestRatesScreen> {
            val args = it.toRoute<ScreenRoute.LatestRatesScreen>()
            LatestRatesScreen(
                navController = navController,
                base = args.base
            )
        }
    }
}