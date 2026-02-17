package cz.musilto5.myflickerapp.presentation.feature.image.list.viewModel

import androidx.lifecycle.ViewModel
import cz.musilto5.myflickerapp.presentation.core.component.SwitchComponent
import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import cz.musilto5.myflickerapp.presentation.feature.image.list.model.ImagesScreenState
import kotlinx.coroutines.flow.StateFlow

abstract class ImagesViewModel : ViewModel() {

    abstract val screenState: StateFlow<ImagesScreenState>
    abstract val textInputComponent: TextInputComponent
    abstract val switchComponent: SwitchComponent

    abstract fun reloadImages()
}
