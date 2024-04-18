package cz.musilto5.myflickerapp.app.di

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
}