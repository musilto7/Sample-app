package cz.musilto5.myflickerapp.app.di

import cz.musilto5.myflickerapp.presentation.core.TextInputComponent
import cz.musilto5.myflickerapp.presentation.feature.list.ImagesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        ImagesViewModel(
            textInputComponent = TextInputComponent(
                initialText = "",
                get(),
                uniqueComponentName = "images_text_input"
            ),
            repository = get()
        )
    }
}