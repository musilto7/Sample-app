package cz.musilto5.myflickerapp.domain.feature.images.model

import kotlin.time.Instant

data class FlickerImage(
    val title : String?,
    val imageUrl: String,
    val dateTaken: Instant,
)
