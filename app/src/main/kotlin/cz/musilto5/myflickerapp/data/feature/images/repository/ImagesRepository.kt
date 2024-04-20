package cz.musilto5.myflickerapp.data.feature.images.repository

import cz.musilto5.myflickerapp.data.api.service.FlickerApi
import cz.musilto5.myflickerapp.domain.core.ErrorType
import cz.musilto5.myflickerapp.domain.core.Result
import cz.musilto5.myflickerapp.domain.core.map
import cz.musilto5.myflickerapp.domain.feature.images.model.FlickerImage
import org.openapitools.client.infrastructure.HttpResponse
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

class ImagesRepository(
    private val flickerApi: FlickerApi,
) {

    suspend fun downloadImages(tags: List<String>): Result<List<FlickerImage>> {
        return callApi { flickerApi.getImages(tags = tags.joinToString(",")) }
            .map {
                it.items.map {
                    FlickerImage(
                        title = it.title.takeIf { it.trim().isNotEmpty() },
                        imageUrl = it.media.m
                    )
                }
            }
    }

    private suspend fun <T : Any> callApi(function: suspend () -> HttpResponse<T>): Result<T> {
        return try {
            val response = function()
            if (response.success) {
                Result.Success(response.body())
            } else {
                Result.Error(ErrorType.NetworkBadState(response.status))
            }
        } catch (e: InterruptedException) {
            throw e
        } catch (e: UnknownHostException) {
            Result.Error(ErrorType.Network)
        } catch (e: SSLHandshakeException) {
            Result.Error(ErrorType.Network)
        } catch (e: Throwable) {
            Result.Error(ErrorType.Unknown)
        }
    }
}