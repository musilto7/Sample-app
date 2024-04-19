package cz.musilto5.myflickerapp.presentation.feature.list

data class ImagesViewState(
    val tagsInput: String,
    val images: List<String>,
    val isLoading: Boolean,
    val errorMessage : String?,
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