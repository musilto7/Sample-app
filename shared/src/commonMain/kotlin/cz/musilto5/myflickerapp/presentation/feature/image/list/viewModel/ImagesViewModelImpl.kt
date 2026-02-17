package cz.musilto5.myflickerapp.presentation.feature.image.list.viewModel

import androidx.lifecycle.viewModelScope
import cz.musilto5.myflickerapp.domain.feature.images.model.FlickerImage
import cz.musilto5.myflickerapp.domain.feature.images.repository.ImagesRepository
import cz.musilto5.myflickerapp.presentation.core.component.SwitchComponent
import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import cz.musilto5.myflickerapp.presentation.core.error.ErrorMapper
import cz.musilto5.myflickerapp.presentation.feature.image.list.model.ImagesScreenState
import cz.musilto5.myflickerapp.presentation.feature.image.model.FlickerImageVO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class ImagesViewModelImpl(
    override val textInputComponent: TextInputComponent,
    override val switchComponent: SwitchComponent,
    repository: ImagesRepository,
) : ImagesViewModel() {

    private val imagesStateManager = ImagesStateManager(
        scope = viewModelScope,
        textInputComponent = textInputComponent,
        switchComponent = switchComponent,
        repository = repository,
    )

    override val screenState: StateFlow<ImagesScreenState> = imagesStateManager.state
        .map(::mapToScreenState)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ImagesScreenState.IDLE
        )

    override fun reloadImages() = imagesStateManager.reloadImages()

    private fun mapToScreenState(managerState: ImagesStateManagerState): ImagesScreenState =
        ImagesScreenState(
            tagsInput = managerState.tagsInput,
            images = managerState.images.map(::toViewObject),
            isLoading = managerState.isLoading,
            errorResource = managerState.error?.let(ErrorMapper::mapToResource),
        )

    private fun toViewObject(flickerImage: FlickerImage) =
        FlickerImageVO(
            title = flickerImage.title,
            imageUrl = flickerImage.imageUrl
        )
}
