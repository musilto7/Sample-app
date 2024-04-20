package cz.musilto5.myflickerapp.presentation.core

import androidx.lifecycle.SavedStateHandle
import org.junit.Assert
import org.junit.Test

class TextInputComponentTest {

    private lateinit var tested: TextInputComponent

    @Test
    fun `initial text should be put in viewState`() {
        initializeComponent("initial text")
        Assert.assertEquals(TextInputComponentModel("initial text"), tested.viewState.value)
    }

    @Test
    fun `change text and verify viewState`() {
        initializeComponent("initial text")
        tested.updateText("new text")
        Assert.assertEquals("new text", tested.viewState.value.text)
    }

    @Test
    fun `restore state from savedState hande`() {
        val savedStateHandle = SavedStateHandle()
        initializeComponent("first text", savedStateHandle)
        initializeComponent("second text", savedStateHandle)
        Assert.assertEquals("first text", tested.viewState.value.text)
    }

    @Test
    fun `state is not restored when unique component name differs`() {
        val savedStateHandle = SavedStateHandle()
        initializeComponent("first text", savedStateHandle, uniqueComponentName = "component1")
        initializeComponent("second text", savedStateHandle, uniqueComponentName = "component2")
        Assert.assertEquals("second text", tested.viewState.value.text)
    }

    private fun initializeComponent(
        initialText: String,
        stateHandle: SavedStateHandle = SavedStateHandle(),
        uniqueComponentName: String = "text_input_component",
    ) {
        tested = TextInputComponent(initialText, stateHandle, uniqueComponentName)
    }

}