package cz.musilto5.myflickerapp.presentation.feature.list.model

data class ImagesViewState(
    val tagsInput: String,
    val images: List<FlickerImageVO>,
    val isLoading: Boolean,
    val errorMessage: String?,
) {
    companion object {
        val IDLE = ImagesViewState(
            tagsInput = "",
            images = emptyList(),
            isLoading = false,
            errorMessage = null,
        )
    }
}