package br.com.dev360.globalwalletexplorer.featurehome.currencies.presentation

import br.com.dev360.globalwalletexplorer.corenetwork.helper.ApiResult
import br.com.dev360.globalwalletexplorer.coresharedui.helpers.UiText
import br.com.dev360.globalwalletexplorer.featurehome.currencies.domain.CurrenciesContracts

class FakeCurrenciesUiModel(
    private val uiText: UiText
) : CurrenciesContracts.UiModel {
    override fun getEmptyListText(): UiText = uiText

    override fun getFailureText(failure: ApiResult.Failure): UiText = uiText
}