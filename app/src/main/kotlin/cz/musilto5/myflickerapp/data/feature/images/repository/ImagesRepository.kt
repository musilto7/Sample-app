package cz.musilto5.myflickerapp.data.feature.images.repository

import cz.musilto5.myflickerapp.data.api.service.FlickerApi
import cz.musilto5.myflickerapp.data.core.callApi
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
            .map { imageResponse ->
                imageResponse.items.map { image ->
                    FlickerImage(
                        title = image.title.takeIf { it.trim().isNotEmpty() },
                        imageUrl = image.media.m
                    )
                }
            }
    }
}