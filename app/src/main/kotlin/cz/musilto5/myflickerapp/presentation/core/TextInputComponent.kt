package cz.musilto5.myflickerapp.presentation.core

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.StateFlow

class TextInputComponent(
    initialText: String,
    private val savedStateHandle: SavedStateHandle,
    private val uniqueComponentName: String,
) {

    val viewState: StateFlow<TextInputComponentModel> = savedStateHandle.getStateFlow(
        getSavedStateHandleKey(), TextInputComponentModel(initialText)
    )

    fun updateText(text: String) {
        savedStateHandle[getSavedStateHandleKey()] = TextInputComponentModel(text)
    }

    private fun getSavedStateHandleKey() = "$COMPONENT_NAME-$uniqueComponentName"

    companion object {
        private const val COMPONENT_NAME = "text_input_component"
    }
}