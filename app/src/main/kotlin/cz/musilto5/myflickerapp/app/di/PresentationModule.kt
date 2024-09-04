package cz.musilto5.myflickerapp.app.di

import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import cz.musilto5.myflickerapp.presentation.feature.image.list.viewModel.ImagesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        ImagesViewModel(
            textInputComponent = TextInputComponent(
                initialText = "",
                get(),
                uniqueComponentName = "images_text_input"
            ),
            repository = get(),
            savedStateHandle = get(),
        )
    }
}
