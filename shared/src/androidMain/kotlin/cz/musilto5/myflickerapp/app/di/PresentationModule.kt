package cz.musilto5.myflickerapp.app.di

import cz.musilto5.myflickerapp.presentation.ImagesViewModel
import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    single { TextInputComponent(initialText = "") }
    viewModel { ImagesViewModel(get(), get(), get()) }
}
