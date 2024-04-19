package cz.musilto5.myflickerapp.presentation.feature.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.musilto5.myflickerapp.data.feature.images.repository.ImagesRepository
import cz.musilto5.myflickerapp.domain.core.ErrorType
import cz.musilto5.myflickerapp.domain.core.onError
import cz.musilto5.myflickerapp.domain.core.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ImagesViewModel(
    private val repository: ImagesRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _savedState: StateFlow<ImagesSavedState> = savedStateHandle.getStateFlow(
        KEY_SAVED_STATE,
        ImagesSavedState.IDLE
    )

    private val _viewState: MutableStateFlow<ImagesViewState> =
        MutableStateFlow(ImagesViewState.IDLE)
    val viewStates: StateFlow<ImagesViewState> = _viewState

    init {
        viewModelScope.launch {
            _savedState.debounce(300L).collect { savedState ->
                fetchImages(savedState.tagsInput)
            }
        }
    }

    private suspend fun fetchImages(tagsInput: String) {
        _viewState.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
                tagsInput = tagsInput,
            )
        }
        repository.downloadImages(tagsInput.split(WHITE_SPACE_REGEX).filter { it.isNotBlank() })
            .onSuccess { images ->
                _viewState.update {
                    it.copy(isLoading = false, errorMessage = null, images = images)
                }
            }.onError { errorType, throwable ->
                _viewState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = toErrorMessage(errorType, throwable),
                        images = emptyList(),
                    )
                }
            }
    }

    private fun toErrorMessage(errorType: ErrorType, throwable: Throwable?): String? {
        return "Something went wrong"
    }

    fun updatesTagsInput(tagsInput: String) {
        savedStateHandle[KEY_SAVED_STATE] =
            _savedState.value.copy(tagsInput = tagsInput)
    }

    companion object {
        private const val KEY_SAVED_STATE = "ImagesViewModelState"
        val WHITE_SPACE_REGEX = "\\s+".toRegex()
    }

}