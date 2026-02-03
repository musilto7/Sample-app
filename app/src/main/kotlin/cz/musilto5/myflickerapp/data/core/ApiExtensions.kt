package cz.musilto5.myflickerapp.data.core

import cz.musilto5.myflickerapp.domain.core.Error
import cz.musilto5.myflickerapp.domain.core.Result
import org.openapitools.client.infrastructure.HttpResponse
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

suspend fun <T : Any> callApi(function: suspend () -> HttpResponse<T>): Result<T, Error> {
    return try {
        val response = function()
        if (response.success) {
            Result.Success(response.body())
        } else {
            Result.Error(Error.NetworkBadState(response.status))
        }
    } catch (e: InterruptedException) {
        throw e
    } catch (e: UnknownHostException) {
        Result.Error(Error.Network)
    } catch (e: SSLHandshakeException) {
        Result.Error(Error.Network)
    } catch (e: Throwable) {
        Result.Error(Error.Unknown)
    }
}
