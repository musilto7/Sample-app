package cz.musilto5.myflickerapp.app.di

import cz.musilto5.myflickerapp.data.feature.images.repository.ImagesRepositoryImpl
import cz.musilto5.myflickerapp.domain.feature.images.repository.ImagesRepository
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.OkHttpClient
import org.koin.dsl.module

val dataModule = module {
    single { provideApiFlickerApi(httpClientEngine = get()) }

    single {
        OkHttp.create {
            preconfigured = get()
        }
    }

    single { OkHttpClient.Builder().build() }

    single<ImagesRepository> {
        ImagesRepositoryImpl(
            flickerApi = get()
        )
    }
}