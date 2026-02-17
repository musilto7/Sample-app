package cz.musilto5.myflickerapp.presentation.core

import androidx.lifecycle.SavedStateHandle
import cz.musilto5.myflickerapp.presentation.core.component.SwitchComponent
import org.junit.Assert
import org.junit.Test

class SwitchComponentTest {

    private lateinit var tested: SwitchComponent

    @Test
    fun `initial checked state false should be reflected in checkedState`() {
        initializeComponent(SavedStateHandle(), stateKey = "switch_key", initialChecked = false)
        Assert.assertFalse(tested.checkedState.value)
    }

    @Test
    fun `initial checked state true should be reflected in checkedState`() {
        initializeComponent(SavedStateHandle(), stateKey = "switch_key", initialChecked = true)
        Assert.assertTrue(tested.checkedState.value)
    }

    @Test
    fun `setChecked true updates checkedState`() {
        initializeComponent(SavedStateHandle(), stateKey = "switch_key", initialChecked = false)
        tested.setChecked(true)
        Assert.assertTrue(tested.checkedState.value)
    }

    @Test
    fun `setChecked false updates checkedState`() {
        initializeComponent(SavedStateHandle(), stateKey = "switch_key", initialChecked = true)
        tested.setChecked(false)
        Assert.assertFalse(tested.checkedState.value)
    }

    @Test
    fun `state is restored from savedStateHandle when reusing same handle and key`() {
        val savedStateHandle = SavedStateHandle()
        initializeComponent(savedStateHandle, stateKey = "switch_key", initialChecked = false)
        tested.setChecked(true)
        initializeComponent(savedStateHandle, stateKey = "switch_key", initialChecked = false)
        Assert.assertTrue(tested.checkedState.value)
    }

    @Test
    fun `state is not shared when state key differs`() {
        val savedStateHandle = SavedStateHandle()
        initializeComponent(savedStateHandle, stateKey = "key1", initialChecked = false)
        tested.setChecked(true)
        initializeComponent(savedStateHandle, stateKey = "key2", initialChecked = false)
        Assert.assertFalse(tested.checkedState.value)
    }

    private fun initializeComponent(
        savedStateHandle: SavedStateHandle,
        stateKey: String,
        initialChecked: Boolean = false,
    ) {
        tested = SwitchComponent(savedStateHandle, stateKey, initialChecked)
    }
}
