package cz.musilto5.myflickerapp.presentation.core.component

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TextInputComponent(
    initialText: String,
    private val savedStateHandle: SavedStateHandle,
    uniqueComponentName: String = "text_input_component",
) {
    private val stateKey = "TextInputComponent:$uniqueComponentName:text"

    private val _viewState = MutableStateFlow(
        TextInputComponentModel(
            text = savedStateHandle[stateKey] ?: initialText
        )
    )
    val viewState: StateFlow<TextInputComponentModel> = _viewState.asStateFlow()

    fun updateText(text: String) {
        _viewState.value = TextInputComponentModel(text)
        savedStateHandle[stateKey] = text
    }
}
