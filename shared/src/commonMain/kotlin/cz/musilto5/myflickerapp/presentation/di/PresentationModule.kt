package cz.musilto5.myflickerapp.presentation.di

import cz.musilto5.myflickerapp.presentation.feature.image.list.ImagesViewModel
import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
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
                uniqueComponentName = "images_text_input"
            )
        )
    }
}
