package cz.musilto5.myflickerapp.presentation.core.component

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.StateFlow

class SwitchComponent(
    private val savedStateHandle: SavedStateHandle,
    private val stateKey: String,
    initialChecked: Boolean = false,
) {
    val checkedState: StateFlow<Boolean> = savedStateHandle.getStateFlow(stateKey, initialChecked)

    fun setChecked(checked: Boolean) {
        savedStateHandle[stateKey] = checked
    }
}
