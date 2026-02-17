package cz.musilto5.myflickerapp.presentation.feature.image.list.viewModel

import cz.musilto5.myflickerapp.domain.core.Error
import cz.musilto5.myflickerapp.domain.feature.images.model.FlickerImage

internal data class ImagesStateManagerState(
    val tagsInput: String,
    val images: List<FlickerImage>,
    val isLoading: Boolean,
    val error: Error?,
) {
    companion object {
        val IDLE = ImagesStateManagerState(
            tagsInput = "",
            images = emptyList(),
            isLoading = false,
            error = null,
        )
    }
}
