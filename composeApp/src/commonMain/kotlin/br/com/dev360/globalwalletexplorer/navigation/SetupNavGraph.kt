package br.com.dev360.globalwalletexplorer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.dev360.globalwalletexplorer.featurehome.presentation.HomeScreen

@Composable
fun SetupNavGraph(
    startDestination: ScreenRoute = ScreenRoute.HomeScreen
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<ScreenRoute.HomeScreen> {
            HomeScreen()
        }
    }
}