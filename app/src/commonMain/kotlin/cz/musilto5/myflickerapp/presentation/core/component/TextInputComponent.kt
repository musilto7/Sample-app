package cz.musilto5.myflickerapp.presentation.core.component

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TextInputComponent(initialText: String) {
    private val _viewState = MutableStateFlow(TextInputComponentModel(initialText))
    val viewState: StateFlow<TextInputComponentModel> = _viewState.asStateFlow()

    fun updateText(text: String) {
        _viewState.value = TextInputComponentModel(text)
    }
}
