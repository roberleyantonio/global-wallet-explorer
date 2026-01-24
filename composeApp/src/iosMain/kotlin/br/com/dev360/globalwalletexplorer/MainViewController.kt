package br.com.dev360.globalwalletexplorer

import androidx.compose.ui.window.ComposeUIViewController
import br.com.dev360.globalwalletexplorer.di.initializeKoin
import br.com.dev360.globalwalletexplorer.presentation.App

fun MainViewController() = ComposeUIViewController { App() }

fun initKoinIos() {
    initializeKoin ()
}