package cz.musilto5.myflickerapp.app.di

import cz.musilto5.myflickerapp.data.core.createFlickerApi
import cz.musilto5.myflickerapp.data.feature.images.repository.ImagesRepositoryImpl
import cz.musilto5.myflickerapp.domain.feature.images.repository.ImagesRepository
import cz.musilto5.myflickerapp.platform.createHttpClientEngine
import cz.musilto5.myflickerapp.presentation.core.component.TextInputComponent
import cz.musilto5.myflickerapp.presentation.feature.image.list.viewModel.ImagesScreenStateHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.dsl.module

val iosDataModule = module {
    single { createHttpClientEngine() }
    single { createFlickerApi(get()) }
    single<ImagesRepository> { ImagesRepositoryImpl(flickerApi = get()) }
}

private val switchState = MutableStateFlow(false)
