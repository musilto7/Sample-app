package cz.musilto5.myflickerapp.presentation.core.component.tooling

import androidx.lifecycle.SavedStateHandle
import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import java.util.UUID

@Suppress("FunctionName")
fun TextInputComponentStub(text: String) = TextInputComponent(
    initialText = text,
    savedStateHandle = SavedStateHandle(),
    uniqueComponentName = UUID.randomUUID().toString()
)