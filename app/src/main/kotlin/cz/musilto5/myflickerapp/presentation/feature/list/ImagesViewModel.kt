package cz.musilto5.myflickerapp.presentation.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.musilto5.myflickerapp.data.feature.images.repository.ImagesRepository
import cz.musilto5.myflickerapp.domain.core.ErrorType
import cz.musilto5.myflickerapp.domain.core.onError
import cz.musilto5.myflickerapp.domain.core.onSuccess
import cz.musilto5.myflickerapp.domain.feature.images.model.FlickerImage
import cz.musilto5.myflickerapp.presentation.core.TextInputComponent
import cz.musilto5.myflickerapp.presentation.feature.list.model.FlickerImageVO
import cz.musilto5.myflickerapp.presentation.feature.list.model.ImagesViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ImagesViewModel(
    val textInputComponent: TextInputComponent,
    private val repository: ImagesRepository,
) : ViewModel() {


    private val _viewState: MutableStateFlow<ImagesViewState> =
        MutableStateFlow(ImagesViewState.IDLE)
    val viewStates: StateFlow<ImagesViewState> = _viewState

    init {
        viewModelScope.launch {
            textInputComponent.viewState.debounce(300L).collect { textInputModel ->
                fetchImages(textInputModel.text)
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
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        images = images.map { toViewObject(it) })
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

    private fun toViewObject(flickerImage: FlickerImage): FlickerImageVO {
        return FlickerImageVO(
            title = flickerImage.title,
            imageUrl = flickerImage.imageUrl
        )
    }


    private fun toErrorMessage(errorType: ErrorType, throwable: Throwable?): String? {
        return "Something went wrong"
    }


    companion object {
        val WHITE_SPACE_REGEX = "\\s+".toRegex()
    }
}