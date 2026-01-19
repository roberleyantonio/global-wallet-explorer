package br.com.dev360.globalwalletexplorer.featurehome.presentation

import br.com.dev360.globalwalletexplorer.corenetwork.AvailableCurrenciesQuery
import br.com.dev360.globalwalletexplorer.corenetwork.helper.ApiResult
import br.com.dev360.globalwalletexplorer.coresharedui.helpers.UiText
import br.com.dev360.globalwalletexplorer.featurehome.domain.HomeContracts
import br.com.dev360.globalwalletexplorer.featurehome.domain.model.CurrencyItem
import globalwalletexplorer.feature.home.generated.resources.Res
import globalwalletexplorer.feature.home.generated.resources.error_no_internet
import globalwalletexplorer.feature.home.generated.resources.error_server
import globalwalletexplorer.feature.home.generated.resources.error_unknown
import org.koin.core.annotation.Factory

@Factory
class HomeUiModelImpl : HomeContracts.UiModel {
    override fun getFailureText(failure: ApiResult.Failure): UiText {
        return when (failure) {
            is ApiResult.Failure.ApiError -> UiText.DynamicString(failure.message)
            is ApiResult.Failure.HttpError -> UiText.Resource(
                Res.string.error_server,
                listOf(failure.code)
            )

            is ApiResult.Failure.NetworkError -> UiText.Resource(Res.string.error_no_internet)
            else -> UiText.Resource(Res.string.error_unknown)
        }
    }

    override fun mapToCurrencyList(apiData: List<AvailableCurrenciesQuery.Currency>): List<CurrencyItem> {
        return apiData.map {
            CurrencyItem(
                id = it.code,
                name = it.name,
                symbol = "",
                code = it.code
            )
        }
    }

}