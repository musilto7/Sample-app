package cz.musilto5.myflickerapp.data.core

import cz.musilto5.myflickerapp.domain.core.Error
import cz.musilto5.myflickerapp.domain.core.Result
import cz.musilto5.myflickerapp.platform.isNetworkError
import org.openapitools.client.infrastructure.HttpResponse

suspend fun <T : Any> callApi(function: suspend () -> HttpResponse<T>): Result<T, Error> {
    return try {
        val response = function()
        if (response.success) {
            Result.Success(response.body())
        } else {
            Result.Error(Error.NetworkBadState(response.status))
        }
    } catch (e: Throwable) {
        if (isNetworkError(e)) {
            Result.Error(Error.Network)
        } else {
            Result.Error(Error.Unknown)
        }
    }
}
