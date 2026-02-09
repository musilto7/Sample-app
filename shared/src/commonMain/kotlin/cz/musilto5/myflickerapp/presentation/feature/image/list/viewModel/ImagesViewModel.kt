package cz.musilto5.myflickerapp.presentation.feature.image.list.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.musilto5.myflickerapp.domain.feature.images.repository.ImagesRepository
import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import cz.musilto5.myflickerapp.presentation.feature.image.ImageConstants

class ImagesViewModel(
    private val repository: ImagesRepository,
    private val savedStateHandle: SavedStateHandle,
    private val textInputComponent: TextInputComponent,
) : ViewModel() {

    val stateHolder: ImagesScreenState = ImagesScreenState(
        scope = viewModelScope,
        textInputComponent = textInputComponent,
        repository = repository,
        switchState = savedStateHandle.getStateFlow(ImageConstants.SWITCH_STATE_KEY, false),
        saveSwitchState = { savedStateHandle[ImageConstants.SWITCH_STATE_KEY] = it }
    )
}
