package cz.musilto5.myflickerapp.app.di

import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import cz.musilto5.myflickerapp.presentation.feature.image.ImageConstants
import cz.musilto5.myflickerapp.presentation.feature.image.list.viewModel.ImagesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        ImagesViewModel(
            repository = get(),
            savedStateHandle = get(),
            textInputComponent = TextInputComponent(
                initialText = "",
                savedStateHandle = get(),
                uniqueComponentName = ImageConstants.TEXT_INPUT_KEY
            )
        )
    }
}
