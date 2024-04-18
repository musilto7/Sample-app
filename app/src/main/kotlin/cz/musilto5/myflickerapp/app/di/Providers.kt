package cz.musilto5.myflickerapp.app.di

import cz.musilto5.myflickerapp.data.api.service.FlickerApi
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType

fun provideApiFlickerApi(
    httpClientEngine: HttpClientEngine,
): FlickerApi {

    val config: (HttpClientConfig<*>.() -> Unit) = {

        defaultRequest {
            contentType(ContentType.Application.Json)
        }

        install(Logging) {
            level = LogLevel.BODY
        }

    }

    return FlickerApi(httpClientEngine = httpClientEngine, httpClientConfig = config)
}