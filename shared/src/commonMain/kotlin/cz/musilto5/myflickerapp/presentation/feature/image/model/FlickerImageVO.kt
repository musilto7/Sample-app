package cz.musilto5.myflickerapp.presentation.feature.image.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FlickerImageVO(
    @SerialName("title")
    val title: String?,
    @SerialName("imageUrl")
    val imageUrl: String
)
