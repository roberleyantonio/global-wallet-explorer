package br.com.dev360.globalwalletexplorer.corenetwork.helper

sealed interface ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>
    sealed interface Failure : ApiResult<Nothing> {
        data class HttpError(val code: Int, val message: String) : Failure
        data class ApiError(val message: String, val errors: List<String>) : Failure
        data class NetworkError(val throwable: Throwable) : Failure
        data object UnknownError : Failure
    }
}

inline fun <T, R> ApiResult<T>.mapSuccess(transform: (T) -> R): ApiResult<R> {
    return when (this) {
        is ApiResult.Success -> ApiResult.Success(transform(data))
        is ApiResult.Failure -> this
    }
}

inline fun <T> ApiResult<T>.onHandle(
    success: (T) -> Unit,
    failure: (ApiResult.Failure) -> Unit
) {
    when (this) {
        is ApiResult.Success -> success(data)
        is ApiResult.Failure -> failure(this)
    }
}