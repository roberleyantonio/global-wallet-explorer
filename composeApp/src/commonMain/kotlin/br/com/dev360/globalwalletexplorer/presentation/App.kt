package br.com.dev360.globalwalletexplorer.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import br.com.dev360.globalwalletexplorer.navigation.SetupNavGraph

@Composable
fun App() {
    MaterialTheme {
        SetupNavGraph()
    }
}