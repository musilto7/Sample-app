package cz.musilto5.myflickerapp.presentation.feature.image.preview

import androidx.lifecycle.SavedStateHandle
import cz.musilto5.myflickerapp.generated.resources.Res
import cz.musilto5.myflickerapp.generated.resources.error_network
import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import cz.musilto5.myflickerapp.presentation.feature.image.list.model.ImagesViewState
import cz.musilto5.myflickerapp.presentation.feature.image.model.FlickerImageVO

object ImagesScreenPreviewData {

    val sampleImages = listOf(
        FlickerImageVO(
            title = "Beautiful Sunset",
            imageUrl = "https://via.placeholder.com/300x400"
        ),
        FlickerImageVO(
            title = "Mountain Landscape",
            imageUrl = "https://via.placeholder.com/300x250"
        ),
        FlickerImageVO(
            title = "Ocean Waves",
            imageUrl = "https://via.placeholder.com/300x350"
        ),
        FlickerImageVO(
            title = "City Lights",
            imageUrl = "https://via.placeholder.com/300x300"
        ),
        FlickerImageVO(
            title = null,
            imageUrl = "https://via.placeholder.com/300x280"
        )
    )

    val loadedViewState = ImagesViewState(
        tagsInput = "nature",
        images = sampleImages,
        isLoading = false,
        errorResource = null
    )

    val loadingViewState = ImagesViewState(
        tagsInput = "loading",
        images = emptyList(),
        isLoading = true,
        errorResource = null
    )

    val errorViewState = ImagesViewState(
        tagsInput = "error",
        images = emptyList(),
        isLoading = false,
        errorResource = Res.string.error_network
    )

    fun createPreviewTextInputComponent(initialText: String): TextInputComponent {
        return TextInputComponent(
            initialText = initialText,
            savedStateHandle = SavedStateHandle(),
            uniqueComponentName = "preview"
        )
    }
}
