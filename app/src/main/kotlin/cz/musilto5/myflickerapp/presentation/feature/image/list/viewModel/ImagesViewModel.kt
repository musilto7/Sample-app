package cz.musilto5.myflickerapp.presentation.feature.image.list.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.musilto5.myflickerapp.domain.core.Error
import cz.musilto5.myflickerapp.domain.core.onError
import cz.musilto5.myflickerapp.domain.core.onSuccess
import cz.musilto5.myflickerapp.domain.feature.images.model.FlickerImage
import cz.musilto5.myflickerapp.domain.feature.images.model.TagMode
import cz.musilto5.myflickerapp.domain.feature.images.repository.ImagesRepository
import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import cz.musilto5.myflickerapp.presentation.feature.image.list.model.ImagesViewState
import cz.musilto5.myflickerapp.presentation.feature.image.model.FlickerImageVO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ImagesViewModel(
    val textInputComponent: TextInputComponent,
    private val repository: ImagesRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val reloadFlow: MutableStateFlow<Int> = MutableStateFlow(0)

    private val _viewState: MutableStateFlow<ImagesViewState> =
        MutableStateFlow(ImagesViewState.IDLE)
    val viewStates: StateFlow<ImagesViewState> = _viewState.asStateFlow()

    val switchState: StateFlow<Boolean> =
        savedStateHandle.getStateFlow(KEY_SWITCH_STATE, false)

    init {
        viewModelScope.launch {
            reloadFlow.combine(
                textInputComponent.viewState
                    .debounce(300L)
            ) { _, viewState -> viewState }
                .combine(switchState) { textInputModel, switchState ->
                    textInputModel to switchState
                }
                .collect { (textInputModel, switchState )->
                    fetchImages(textInputModel.text, toTagMode(switchState))
                }
        }
    }

    private fun toTagMode(switchState: Boolean): TagMode {
       return if (switchState) TagMode.ALL else TagMode.ANY
    }

    private suspend fun fetchImages(tagsInput: String, tagMode: TagMode) {
        showLoading(tagsInput)
        repository.downloadImages(toTagList(tagsInput), tagMode)
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
                images = images.sortedBy { image -> image.dateTaken }
                    .map { image -> toViewObject(image) })
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

    fun onSwitchCheckedChange(isChecked : Boolean) {
        savedStateHandle[KEY_SWITCH_STATE] = isChecked
    }

    companion object {
        val WHITE_SPACE_REGEX = "\\s+".toRegex()
        private const val KEY_SWITCH_STATE = "KEY_SWITCH_STATE"
    }
}
