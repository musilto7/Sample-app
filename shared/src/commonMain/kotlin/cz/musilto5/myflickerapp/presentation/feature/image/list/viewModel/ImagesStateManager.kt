package cz.musilto5.myflickerapp.presentation.feature.image.list.viewModel

import cz.musilto5.myflickerapp.domain.core.Error
import cz.musilto5.myflickerapp.domain.core.onError
import cz.musilto5.myflickerapp.domain.core.onSuccess
import cz.musilto5.myflickerapp.domain.feature.images.model.FlickerImage
import cz.musilto5.myflickerapp.domain.feature.images.model.TagMode
import cz.musilto5.myflickerapp.domain.feature.images.repository.ImagesRepository
import cz.musilto5.myflickerapp.presentation.core.component.SwitchComponent
import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import cz.musilto5.myflickerapp.presentation.feature.image.ImageConstants
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
internal class ImagesStateManager(
    scope: CoroutineScope,
    private val textInputComponent: TextInputComponent,
    private val switchComponent: SwitchComponent,
    private val repository: ImagesRepository,
) {
    private val reloadFlow = MutableStateFlow(0)

    private val _state = MutableStateFlow(ImagesStateManagerState.IDLE)
    val state: StateFlow<ImagesStateManagerState> = _state.asStateFlow()

    init {
        scope.launch {
            reloadFlow
                .combine(textInputComponent.viewState.debounce(300L)) { _, vs -> vs }
                .combine(switchComponent.checkedState) { textInputModel, checked ->
                    textInputModel to checked
                }
                .collect { (textInputModel, checked) ->
                    fetchImages(textInputModel.text, toTagMode(checked))
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
        _state.update {
            it.copy(isLoading = true, error = null, tagsInput = tagsInput)
        }
    }

    private fun toTagList(tagsInput: String) =
        tagsInput.split(ImageConstants.TAG_SEPARATOR_REGEX).filter { it.isNotBlank() }

    private fun onFetchSuccess(images: List<FlickerImage>) {
        _state.update {
            it.copy(
                isLoading = false,
                error = null,
                images = images.sortedBy { img -> img.dateTaken }
            )
        }
    }

    private fun onFetchError(errorType: Error, throwable: Throwable?) {
        _state.update {
            it.copy(
                isLoading = false,
                error = errorType,
                images = emptyList()
            )
        }
    }

    fun reloadImages() {
        reloadFlow.update { it + 1 }
    }
}
