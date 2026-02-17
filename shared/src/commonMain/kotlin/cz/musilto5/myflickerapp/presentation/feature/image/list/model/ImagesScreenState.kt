package cz.musilto5.myflickerapp.presentation.feature.image.list.model

import cz.musilto5.myflickerapp.presentation.feature.image.model.FlickerImageVO
import org.jetbrains.compose.resources.StringResource

data class ImagesScreenState(
    val tagsInput: String,
    val images: List<FlickerImageVO>,
    val isLoading: Boolean,
    val errorResource: StringResource?,
) {
    companion object {
        val IDLE = ImagesScreenState(
            tagsInput = "",
            images = emptyList(),
            isLoading = false,
            errorResource = null,
        )
    }
}
