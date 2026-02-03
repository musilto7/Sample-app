package cz.musilto5.myflickerapp.presentation.feature.image.list.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.musilto5.myflickerapp.data.feature.images.repository.ImagesRepository
import cz.musilto5.myflickerapp.domain.core.Error
import cz.musilto5.myflickerapp.domain.core.onError
import cz.musilto5.myflickerapp.domain.core.onSuccess
import cz.musilto5.myflickerapp.domain.feature.images.model.FlickerImage
import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import cz.musilto5.myflickerapp.presentation.feature.image.list.model.ImagesViewState
import cz.musilto5.myflickerapp.presentation.feature.image.model.FlickerImageVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ImagesViewModel(
    val textInputComponent: TextInputComponent,
    private val repository: ImagesRepository,
) : ViewModel() {

    private val reloadFlow: MutableStateFlow<Int> = MutableStateFlow(0)

    private val _viewState: MutableStateFlow<ImagesViewState> =
        MutableStateFlow(ImagesViewState.IDLE)
    val viewStates: StateFlow<ImagesViewState> = _viewState

    init {
        viewModelScope.launch {
            reloadFlow.combine(
                textInputComponent.viewState
                    .debounce(300L)
            ) { _, viewState -> viewState }
                .collect { textInputModel ->
                    fetchImages(textInputModel.text)
                }
        }
    }

    private suspend fun fetchImages(tagsInput: String) {
        showLoading(tagsInput)
        repository.downloadImages(toTagList(tagsInput))
            .onSuccess(::onFetchSuccess)
            .onError(::onFetchError)
    }

    private fun showLoading(tagsInput: String) {
        _viewState.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
                tagsInput = tagsInput,
            )
        }
    }

    private fun toTagList(tagsInput: String) =
        tagsInput.split(WHITE_SPACE_REGEX).filter { it.isNotBlank() }

    private fun onFetchSuccess(images: List<FlickerImage>) {
        _viewState.update {
            it.copy(
                isLoading = false,
                errorMessage = null,
                images = images.map { image -> toViewObject(image) })
        }
    }

    private fun toViewObject(flickerImage: FlickerImage): FlickerImageVO {
        return FlickerImageVO(
            title = flickerImage.title,
            imageUrl = flickerImage.imageUrl
        )
    }

    private fun onFetchError(
        errorType: Error,
        throwable: Throwable?
    ) {
        _viewState.update {
            it.copy(
                isLoading = false,
                errorMessage = toErrorMessage(errorType, throwable),
                images = emptyList(),
            )
        }
    }

    private fun toErrorMessage(errorType: Error, throwable: Throwable?): String {
        return "Something went wrong"
    }

    fun reloadImages() {
        reloadFlow.update { it + 1 }
    }

    companion object {
        val WHITE_SPACE_REGEX = "\\s+".toRegex()
    }
}
