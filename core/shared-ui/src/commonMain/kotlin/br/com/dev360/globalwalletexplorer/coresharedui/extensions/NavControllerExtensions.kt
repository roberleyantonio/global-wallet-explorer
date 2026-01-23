package br.com.dev360.globalwalletexplorer.coresharedui.extensions

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun <T> NavController.setAndNavigate(
    key: String,
    value: T,
    route: Any,
    builder: (NavOptionsBuilder.() -> Unit)? = null
) {
    builder?.let { navigate(route, it) } ?: navigate(route)
    currentBackStackEntry?.savedStateHandle?.set(key, value)
}