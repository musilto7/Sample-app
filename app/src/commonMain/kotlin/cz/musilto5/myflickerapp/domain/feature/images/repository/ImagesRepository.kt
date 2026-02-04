package cz.musilto5.myflickerapp.domain.feature.images.repository

import cz.musilto5.myflickerapp.domain.core.Error
import cz.musilto5.myflickerapp.domain.core.Result
import cz.musilto5.myflickerapp.domain.feature.images.model.FlickerImage
import cz.musilto5.myflickerapp.domain.feature.images.model.TagMode

interface ImagesRepository {
    suspend fun downloadImages(
        tags: List<String>,
        tagMode: TagMode,
    ): Result<List<FlickerImage>, Error>
}
