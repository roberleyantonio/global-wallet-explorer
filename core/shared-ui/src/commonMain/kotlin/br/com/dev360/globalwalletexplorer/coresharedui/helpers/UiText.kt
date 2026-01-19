package br.com.dev360.globalwalletexplorer.coresharedui.helpers

import androidx.compose.runtime.Composable
import br.com.dev360.globalwalletexplorer.coresharedui.helpers.UiText.DynamicString
import br.com.dev360.globalwalletexplorer.coresharedui.helpers.UiText.Resource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

sealed interface UiText {
    data class DynamicString(val value: String) : UiText
    data class Resource(
        val res: StringResource,
        val args: List<Any> = emptyList()
    ) : UiText
}

@Composable
fun UiText.asString(): String {
    return when (this) {
        is DynamicString -> value
        is Resource -> {
            stringResource(res, *args.toTypedArray())
        }
    }
}