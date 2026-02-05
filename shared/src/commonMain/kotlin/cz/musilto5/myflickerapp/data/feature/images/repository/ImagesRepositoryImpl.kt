package cz.musilto5.myflickerapp.data.feature.images.repository

import cz.musilto5.myflickerapp.data.api.service.FlickerApi
import cz.musilto5.myflickerapp.data.core.callApi
import cz.musilto5.myflickerapp.domain.core.map
import cz.musilto5.myflickerapp.domain.feature.images.model.FlickerImage
import cz.musilto5.myflickerapp.domain.feature.images.model.TagMode
import cz.musilto5.myflickerapp.domain.feature.images.repository.ImagesRepository

class ImagesRepositoryImpl(
    private val flickerApi: FlickerApi,
) : ImagesRepository {

    override suspend fun downloadImages(
        tags: List<String>,
        tagMode: TagMode,
    ): cz.musilto5.myflickerapp.domain.core.Result<List<FlickerImage>, cz.musilto5.myflickerapp.domain.core.Error> {
        return callApi {
            flickerApi.getImages(
                tags = tags.joinToString(","),
                tagmode = tagMode.value
            )
        }.map { imageResponse ->
            imageResponse.items.map { image ->
                FlickerImage(
                    title = image.title.takeIf { it.trim().isNotEmpty() },
                    imageUrl = image.media.m,
                    dateTaken = image.dateTaken,
                )
            }
        }
    }
}
