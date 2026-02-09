package cz.musilto5.myflickerapp.presentation.feature.image.list.viewModel

import cz.musilto5.myflickerapp.domain.core.Error
import cz.musilto5.myflickerapp.domain.core.onError
import cz.musilto5.myflickerapp.domain.core.onSuccess
import cz.musilto5.myflickerapp.domain.feature.images.model.FlickerImage
import cz.musilto5.myflickerapp.domain.feature.images.model.TagMode
import cz.musilto5.myflickerapp.domain.feature.images.repository.ImagesRepository
import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import cz.musilto5.myflickerapp.presentation.core.error.ErrorMapper
import cz.musilto5.myflickerapp.presentation.feature.image.ImageConstants
import cz.musilto5.myflickerapp.presentation.feature.image.list.model.ImagesViewState
import cz.musilto5.myflickerapp.presentation.feature.image.model.FlickerImageVO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class ImagesScreenState(
    scope: CoroutineScope,
    val textInputComponent: TextInputComponent,
    private val repository: ImagesRepository,
    val switchState: StateFlow<Boolean>,
    private val saveSwitchState: (Boolean) -> Unit,
) {
    private val reloadFlow = MutableStateFlow(0)

    private val _viewState = MutableStateFlow(ImagesViewState.IDLE)
    val viewStates: StateFlow<ImagesViewState> = _viewState.asStateFlow()

    init {
        scope.launch {
            reloadFlow
                .combine(textInputComponent.viewState.debounce(300L)) { _, vs -> vs }
                .combine(switchState) { textInputModel, switchState ->
                    textInputModel to switchState
                }
                .collect { (textInputModel, switchState) ->
                    fetchImages(textInputModel.text, toTagMode(switchState))
                }
        }
    }

    private fun toTagMode(switchState: Boolean): TagMode =
        if (switchState) TagMode.ALL else TagMode.ANY

    private suspend fun fetchImages(tagsInput: String, tagMode: TagMode) {
        showLoading(tagsInput)
        repository.downloadImages(toTagList(tagsInput), tagMode)
            .onSuccess(::onFetchSuccess)
            .onError(::onFetchError)
    }

    private fun showLoading(tagsInput: String) {
        _viewState.update {
            it.copy(isLoading = true, errorResource = null, tagsInput = tagsInput)
        }
    }

    private fun toTagList(tagsInput: String) =
        tagsInput.split(ImageConstants.TAG_SEPARATOR_REGEX).filter { it.isNotBlank() }

    private fun onFetchSuccess(images: List<FlickerImage>) {
        _viewState.update {
            it.copy(
                isLoading = false,
                errorResource = null,
                images = images.sortedBy { img -> img.dateTaken }.map(::toViewObject)
            )
        }
    }

    private fun toViewObject(flickerImage: FlickerImage) = FlickerImageVO(
        title = flickerImage.title,
        imageUrl = flickerImage.imageUrl
    )

    private fun onFetchError(errorType: Error, throwable: Throwable?) {
        _viewState.update {
            it.copy(
                isLoading = false,
                errorResource = ErrorMapper.mapToResource(errorType),
                images = emptyList()
            )
        }
    }

    fun reloadImages() {
        reloadFlow.update { it + 1 }
    }

    fun onSwitchCheckedChange(isChecked: Boolean) {
        saveSwitchState(isChecked)
    }
}
