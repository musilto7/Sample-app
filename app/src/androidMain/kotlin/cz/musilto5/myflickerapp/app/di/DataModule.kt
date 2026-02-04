package cz.musilto5.myflickerapp.app.di

import cz.musilto5.myflickerapp.data.core.createFlickerApi
import cz.musilto5.myflickerapp.data.feature.images.repository.ImagesRepositoryImpl
import cz.musilto5.myflickerapp.domain.feature.images.repository.ImagesRepository
import cz.musilto5.myflickerapp.platform.createHttpClientEngine
import org.koin.dsl.module

val dataModule = module {
    single { createHttpClientEngine() }
    single { createFlickerApi(get()) }
    single<ImagesRepository> { ImagesRepositoryImpl(flickerApi = get()) }
}
