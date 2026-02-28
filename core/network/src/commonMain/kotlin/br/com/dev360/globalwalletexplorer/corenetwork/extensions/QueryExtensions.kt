package br.com.dev360.globalwalletexplorer.corenetwork.extensions

import br.com.dev360.globalwalletexplorer.corenetwork.helper.ApiResult
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.cache.normalized.FetchPolicy
import com.apollographql.apollo.cache.normalized.fetchPolicy


suspend inline fun <D : Query.Data> ApolloClient.safeQuery(
    query: Query<D>,
    policy: FetchPolicy? = null
): ApiResult<D> {
    return try {
        var call = this.query(query)

        if (policy != null) {
            call = call.fetchPolicy(policy)
        }

        val response = call.execute()
        val data = response.data

        when {
            response.hasErrors() -> {
                ApiResult.Failure.ApiError(
                    message = "GraphQL Error",
                    errors = response.errors?.map { it.message } ?: emptyList()
                )
            }
            data != null -> ApiResult.Success(data)
            else -> ApiResult.Failure.UnknownError
        }
    } catch (e: Exception) {
        if (e is kotlinx.coroutines.CancellationException) throw e

        if (e is com.apollographql.apollo.exception.ApolloException) {
            ApiResult.Failure.NetworkError(e)
        } else {
            ApiResult.Failure.UnknownError
        }
    }
}