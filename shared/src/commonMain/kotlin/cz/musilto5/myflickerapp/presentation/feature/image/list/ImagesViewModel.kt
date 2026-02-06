package cz.musilto5.myflickerapp.presentation.feature.image.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.musilto5.myflickerapp.domain.feature.images.repository.ImagesRepository
import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import cz.musilto5.myflickerapp.presentation.feature.image.list.viewModel.ImagesScreenStateHolder

private const val KEY_SWITCH_STATE = "KEY_SWITCH_STATE"

class ImagesViewModel(
    private val repository: ImagesRepository,
    private val savedStateHandle: SavedStateHandle,
    private val textInputComponent: TextInputComponent,
) : ViewModel() {

    val stateHolder: ImagesScreenStateHolder = ImagesScreenStateHolder(
        scope = viewModelScope,
        textInputComponent = textInputComponent,
        repository = repository,
        switchState = savedStateHandle.getStateFlow(KEY_SWITCH_STATE, false),
        saveSwitchState = { savedStateHandle[KEY_SWITCH_STATE] = it }
    )
}
