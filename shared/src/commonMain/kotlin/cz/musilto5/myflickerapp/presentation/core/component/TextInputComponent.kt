package cz.musilto5.myflickerapp.presentation.core.component

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable

@Serializable
data class TextInputComponentModel(val text: String)

class TextInputComponent(
    initialText: String,
    private val savedStateHandle: SavedStateHandle,
    uniqueComponentName: String,
) {
    private val stateKey = "$COMPONENT_NAME-$uniqueComponentName"

    private val _viewState = MutableStateFlow(
        TextInputComponentModel(
            text = savedStateHandle[stateKey] ?: initialText.also {
                savedStateHandle[stateKey] = it
            }
        )
    )
    val viewState: StateFlow<TextInputComponentModel> = _viewState.asStateFlow()

    fun updateText(text: String) {
        _viewState.value = TextInputComponentModel(text)
        savedStateHandle[stateKey] = text
    }

    companion object {
        private const val COMPONENT_NAME = "text_input_component"
    }
}
