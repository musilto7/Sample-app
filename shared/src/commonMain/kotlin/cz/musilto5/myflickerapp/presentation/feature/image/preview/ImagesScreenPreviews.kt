package cz.musilto5.myflickerapp.presentation.feature.image.preview

import androidx.lifecycle.SavedStateHandle
import cz.musilto5.myflickerapp.generated.resources.Res
import cz.musilto5.myflickerapp.generated.resources.error_network
import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import cz.musilto5.myflickerapp.presentation.feature.image.list.model.ImagesScreenState
import cz.musilto5.myflickerapp.presentation.feature.image.model.FlickerImageVO

object ImagesScreenPreviews {

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

    val loadedScreenState = ImagesScreenState(
        tagsInput = "nature",
        images = sampleImages,
        isLoading = false,
        errorResource = null
    )

    val loadingScreenState = ImagesScreenState(
        tagsInput = "loading",
        images = emptyList(),
        isLoading = true,
        errorResource = null
    )

    val errorScreenState = ImagesScreenState(
        tagsInput = "error",
        images = emptyList(),
        isLoading = false,
        errorResource = Res.string.error_network
    )

    val emptyScreenState = ImagesScreenState(
        tagsInput = "",
        images = emptyList(),
        isLoading = false,
        errorResource = null
    )

    fun createPreviewTextInputComponent(initialText: String): TextInputComponent {
        return TextInputComponent(
            initialText = initialText,
            savedStateHandle = SavedStateHandle(),
            uniqueComponentName = "preview"
        )
    }
}
